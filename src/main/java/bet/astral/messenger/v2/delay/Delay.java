package bet.astral.messenger.v2.delay;

import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Represents a delay.
 * These delays can be used in different use cases, but they are meant to be used in messages by default
 */
public interface Delay extends DurationLike {
	Delay NONE = new EmptyDelayImpl();
	/**
	 * Returns a delay which uses ticks as the base for duration
	 * @param ticks ticks
	 * @return tick-based delay
	 */
	@NotNull
	static Delay ofTicks(int ticks){
		return new TickDelayImpl(ticks);
	}

	/**
	 * Returns a delay implementation with given delay and time unit
	 * @param delay delay
	 * @param timeUnit time unit
	 * @return new delay
	 */
	@NotNull
	static Delay of(int delay, @NotNull TimeUnit timeUnit) {
		return new DelayImpl(delay, timeUnit);
	}

	/**
	 * Returns the delay (1 = 1 of the time unit)
	 * @return delay
	 */
	long delay();

	/**
	 * Returns the time unit used for this delay
	 * @return time unit
	 */
	@NotNull
	TimeUnit timeUnit();

	/**
	 * Returns this delay as a duration
	 * @return duration
	 */
	default @NotNull Duration asDuration(){
		return Duration.of(delay(), timeUnit().toChronoUnit());
	}

	/**
	 * Returns the delay as minecraft ticks
	 * @return ticks
	 */
	 default long toTicks(){
		return asDuration().toMillis()/ Ticks.SINGLE_TICK_DURATION_MS;
	 }
}
