package bet.astral.messenger.v2.delay;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

final class EmptyDelayImpl implements Delay {
	@Override
	public long delay() {
		return 0;
	}

	@Override
	public @NotNull TimeUnit timeUnit() {
		return TimeUnit.MICROSECONDS;
	}

	@Override
	public @NotNull Duration asDuration() {
		return Duration.ZERO;
	}
}
