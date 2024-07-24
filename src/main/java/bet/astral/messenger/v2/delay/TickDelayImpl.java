package bet.astral.messenger.v2.delay;

import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

final class TickDelayImpl implements Delay {
	private final long ms;

	public TickDelayImpl(long ticks) {
		this.ms = ticks*Ticks.SINGLE_TICK_DURATION_MS;
	}

	@Override
	public long delay() {
		return ms;
	}

	@Override
	public @NotNull TimeUnit timeUnit() {
		return TimeUnit.MILLISECONDS;
	}

	@Override
	public @NotNull Duration asDuration() {
		return Duration.ofMillis(ms);
	}
}
