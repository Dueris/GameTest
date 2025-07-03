package io.github.dueris.gametest.test;

import io.github.dueris.gametest.test.failure.FailureType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

// a test with location data and the player who started it, and if its running, etc
public class RunningTest {
	private final Test test;
	private final BlockPos center;
	private final ServerLevel world;
	private FailureType failureType = null;

	public RunningTest(Test test, BlockPos center, ServerLevel world) {
		this.test = test;
		this.center = center;
		this.world = world;
	}

	// Note: returns null if no failure
	public @Nullable FailureType getFailure() {
		return failureType;
	}

	public void fail(FailureType failureType) {
		this.failureType = failureType;
	}

	public boolean failed() {
		return this.failureType != null;
	}

	public Test getTest() {
		return test;
	}

	public String info() {
		return "RunningTest{" +
				"test=" + this.test.toString() +
				",failed=" + this.failureType +
				",world=" + this.world.toString() +
				",pos=" + this.center.toString() +
				"}";
	}
}
