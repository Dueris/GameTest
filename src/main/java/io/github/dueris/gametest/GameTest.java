package io.github.dueris.gametest;

import io.github.dueris.gametest.test.RunningModTests;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameTest implements ModInitializer {
	private static final List<RunningModTests> RUNNING = new LinkedList<>();
	private static final Map<String, ModInstance> CONTAINERS = new ConcurrentHashMap<>(); // concurrent if a mod does this off-main
	private ModInstance internalMod;

	public static ModInstance getOrThrow(String modName) {
		if (!CONTAINERS.containsKey(modName)) {
			throw new IllegalStateException("Mod by name \"" + modName + "\" is not registered");
		}
		return CONTAINERS.get(modName);
	}

	@Contract(pure = true)
	public static @NotNull Collection<String> getAllMods() {
		return CONTAINERS.keySet();
	}

	public static void tick(MinecraftServer server) {
		for (Iterator<RunningModTests> iterator = RUNNING.iterator(); iterator.hasNext(); ) {
			RunningModTests runningModTests = iterator.next();
			if (!runningModTests.mod().hasTests()) {
				stopRunning(runningModTests); // it has no tasks, so no failures
				iterator.remove();
			}
			// TODO - tick
		}
	}

	@Contract("_ -> new")
	public static @NotNull ModInstance register(String modName) {
		ModInstance instance = new ModInstance(modName);
		if (CONTAINERS.containsKey(modName)) {
			throw new IllegalStateException("Already contains mod by name \"" + modName + "\"");
		}
		CONTAINERS.put(modName, instance);
		return instance;
	}

	public static void startRunning(ModInstance mod, BlockPos pos, ServerPlayer executor) {
		RunningModTests runningModTests = new RunningModTests(mod, pos, executor);
		mod.markRunningTests();
		RUNNING.add(runningModTests);
	}

	public static void stopRunning(@NotNull RunningModTests runningModTests) {
		runningModTests.mod().markFinishedTests(runningModTests.getFailedTests(), runningModTests);
	}

	@Override
	public void onInitialize() {
		this.internalMod = register("gametest");
	}

	public ModInstance getInternalMod() {
		return internalMod;
	}
}
