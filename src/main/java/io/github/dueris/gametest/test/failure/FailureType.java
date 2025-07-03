package io.github.dueris.gametest.test.failure;

import io.github.dueris.gametest.test.RunningTest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface FailureType {
	@Contract(value = "_, _ -> new", pure = true)
	static @NotNull FailureType thrownError(RunningTest test, Throwable error) {
		return new ThrownError(test, error);
	}

	@Contract(value = "_ -> new", pure = true)
	static @NotNull FailureType unexpectedResult(RunningTest test, String reason) {
		return new UnexpectedResult(test, reason);
	}
}
