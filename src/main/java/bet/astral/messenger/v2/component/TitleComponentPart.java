package bet.astral.messenger.v2.component;

import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

/**
 * Represents a title component which can be sent as a subtitle or title.
 * Contains fade in, stay and fade out durations for better title durations.
 */
public interface TitleComponentPart extends ComponentPart{
	/**
	 * Returns the fade in duration for the title
	 * @return fade in duration
	 */
	@NotNull Duration getFadeIn();

	/**
	 * Returns the stay duration for the title
	 * @return stay duration
	 */
	@NotNull Duration getStay();

	/**
	 * Returns the fade out duration for the title
	 * @return fade out duration
	 */
	@NotNull Duration getFadeOut();

	/**
	 * Returns given stay durations in adventure times format
	 * @return times
	 */
	@NotNull
	default Title.Times toTimes(){
		return Title.Times.times(getFadeIn(), getStay(), getFadeOut());
	}
}
