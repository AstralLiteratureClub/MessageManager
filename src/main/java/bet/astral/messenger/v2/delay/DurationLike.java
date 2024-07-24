package bet.astral.messenger.v2.delay;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

/**
 * Duration like is used to represent a duration class.
 * Has only one method and the method's "duty" is to return the class as a duration.
 */
public interface DurationLike {
	/**
	 * Returns given object as a duration
	 * @return duration
	 */
	@NotNull
	Duration asDuration();
}
