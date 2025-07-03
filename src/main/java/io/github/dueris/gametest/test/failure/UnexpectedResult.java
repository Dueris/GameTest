package io.github.dueris.gametest.test.failure;

import io.github.dueris.gametest.test.RunningTest;

class UnexpectedResult implements FailureType {
	private final RunningTest test;

	UnexpectedResult(RunningTest test, String reason) {
		this.test = test;
	}
}
