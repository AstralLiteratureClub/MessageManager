package bet.astral.messenger.v2.locale.source.components;

import bet.astral.platform.scheduler.delay.Delay;
import bet.astral.platform.scheduler.delay.DurationLike;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@Getter
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
		private final int time;
		private final String timeUnit;
		public Duration(int time, TimeUnit timeUnit) {
			this.time = time;
			this.timeUnit = timeUnit.name();
		}

		public Duration(int time, String timeUnit) {
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
			return Delay.of(time, getTimeUnit());
		}

		@Override
		public java.time.@NotNull Duration asDuration() {
			return toDelay().asDuration();
		}
	}
}
