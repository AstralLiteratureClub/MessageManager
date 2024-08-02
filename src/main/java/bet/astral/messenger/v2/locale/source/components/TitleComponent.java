package bet.astral.messenger.v2.locale.source.components;

import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.delay.DurationLike;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@Getter
@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "2.1.0")
public class TitleComponent extends BasicComponent{
	private final Duration fadeIn;
	private final Duration stay;
	private final Duration fadeOut;
	public TitleComponent(String component, Duration fadeIn, Duration stay, Duration fadeOut) {
		super(component);
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	public static class Duration implements DurationLike {
		@Getter
		private final long time;
		private final String timeUnit;
		public Duration(long time, TimeUnit timeUnit) {
			this.time = time;
			this.timeUnit = timeUnit.name();
		}

		public Duration(long time, String timeUnit) {
			this.time = time;
			this.timeUnit = timeUnit;
		}

		public Duration() {
			this.time = 0;
			this.timeUnit = null;
		}

		public TimeUnit getTimeUnit(){
			return TimeUnit.valueOf(timeUnit);
		}

		public Delay toDelay(){
			return Delay.of((int) time, getTimeUnit());
		}

		@Override
		public java.time.@NotNull Duration asDuration() {
			return toDelay().asDuration();
		}
	}
}
