package io.github.dueris.gametest.test;

import io.github.dueris.gametest.ModInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public final class RunningModTests {
	private final ModInstance mod;
	private final BlockPos origin;
	private final ServerPlayer executor;
	private int failed = 0;

	public RunningModTests(ModInstance mod, BlockPos origin, ServerPlayer executor) {
		this.mod = mod;
		this.origin = origin;
		this.executor = executor;
	}

	public ModInstance mod() {
		return mod;
	}

	public BlockPos origin() {
		return origin;
	}

	public ServerPlayer executor() {
		return executor;
	}

	public int getFailedTests() {
		return this.failed;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (RunningModTests) obj;
		return Objects.equals(this.mod, that.mod) &&
				Objects.equals(this.origin, that.origin) &&
				Objects.equals(this.executor, that.executor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mod, origin, executor);
	}

	@Override
	public String toString() {
		return "RunningModTests[" +
				"mod=" + mod + ", " +
				"origin=" + origin + ", " +
				"executor=" + executor + ']';
	}

}
