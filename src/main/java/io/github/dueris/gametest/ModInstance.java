package io.github.dueris.gametest;

import io.github.dueris.gametest.test.RunningModTests;
import io.github.dueris.gametest.test.RunningTest;
import io.github.dueris.gametest.test.Test;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class ModInstance {
	private final String name;
	// all of these are linked so we maintain order
	private final List<Test> tests = new LinkedList<>();
	private final List<Test> pending = new LinkedList<>();
	private final List<RunningTest> failed = new LinkedList<>();
	private boolean isRunningTests = false;

	public ModInstance(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (ModInstance) obj;
		return Objects.equals(this.name, that.name);
	}

	public void registerTest(Test test) {
		if (this.isRunningTests) {
			// move to pending
			this.pending.add(test);
			return;
		}
		this.tests.add(test);
	}

	public void markRunningTests() {
		if (this.isRunningTests) {
			throw new IllegalStateException("Already running tests");
		}
		this.isRunningTests = true;
	}

	public void markFinishedTests(int exitCode, RunningModTests runtimeInstance) {
		if (!this.isRunningTests) {
			throw new IllegalStateException("Already marked finished");
		}
		this.isRunningTests = false;
		MinecraftServer.LOGGER.info("Tests for mod instance \"{}\" has completed {}", this.name, exitCode <= 0 ? "successfully with no failures" : "with " + exitCode + " failures");

		if (!runtimeInstance.executor().hasDisconnected()) {
			runtimeInstance.executor().sendSystemMessage(Component.literal("Tests for mod instance \"" + this.name + "\" has completed " + (exitCode <= 0 ? "successfully with no failures" : "with " + exitCode + " failures")));
		}

		if (exitCode > 0) {
			// has failures, print
			for (RunningTest runningTest : this.failed) {
				MinecraftServer.LOGGER.error("Test {} failed due to {}", runningTest.info(), runningTest.getFailure());
			}
		}
		// clear failures and add all pending tests
		this.failed.clear();
		this.tests.addAll(this.pending);
	}

	public void markFailed(RunningTest test) {
		if (!this.isRunningTests) {
			throw new IllegalStateException("Cannot mark test as failed when not running tests");
		}
	}

	public boolean hasTests() {
		return !this.tests.isEmpty();
	}
}
