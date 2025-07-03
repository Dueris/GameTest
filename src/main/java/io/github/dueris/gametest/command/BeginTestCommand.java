package io.github.dueris.gametest.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.dueris.gametest.GameTest;
import io.github.dueris.gametest.ModInstance;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class BeginTestCommand {
	public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
		dispatcher.register(literal("begintest").requires((stack -> stack.isPlayer() && stack.hasPermission(2)))
				.then(argument("location", Vec3Argument.vec3())
						.then(argument("container", StringArgumentType.word())
								.suggests((context, builder) -> {
									for (String modName : GameTest.getAllMods()) {
										builder.suggest(modName);
									}
									return builder.buildFuture();
								})
								.executes((context) -> {
									CommandSourceStack source = context.getSource();
									if (!source.isPlayer()) {
										CommandConstants.notPlayer(source);
										return 1;
									}
									String modName = StringArgumentType.getString(context, "container");
									ModInstance modInstance = GameTest.getOrThrow(modName);
									ServerPlayer serverPlayer = source.getPlayer();
									BlockPos origin = Objects.requireNonNull(serverPlayer, "how tf is this null?").blockPosition();
									GameTest.startRunning(modInstance, origin, serverPlayer);
									source.sendSuccess(
											() -> Component.literal("Mod container with name of \"" + modName + "\" has been marked for testing"),
											true
									);
									return 0;
								})
						)
				)
		);
	}
}
