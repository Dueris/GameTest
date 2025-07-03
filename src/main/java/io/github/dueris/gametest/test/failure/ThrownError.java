package io.github.dueris.gametest.test.failure;

import io.github.dueris.gametest.test.RunningTest;

class ThrownError implements FailureType {
	private final RunningTest test;
	private final Throwable error;

	public ThrownError(RunningTest test, Throwable error) {
		this.test = test;
		this.error = error;
	}
}
