package bet.astral.messenger.v2.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

/**
 * Component parts are the "chat" messages of base components.
 * Component parts are the components which are sent to players.
 */
public interface ComponentPart {
	/**
	 * Creates a new default component part using given text component
	 * @param component text component
	 * @return new component part
	 */
	static ComponentPart of(@NotNull Component component){
		return new ComponentPartImpl(component);
	}
	/**
	 * Creates a new default component part using given text component
	 * @param component text component
	 * @param hidePrefix true if the prefix should be hidden on component parse, else false
	 * @return new component part
	 */
	static ComponentPart of(@NotNull Component component, boolean hidePrefix){
		return new ComponentPartImpl(component, hidePrefix);
	}

	/**
	 * Creates a new title component part which contains fade in, stay and fade out times.
	 * @param component Text component
	 * @param times times
	 * @return new title component part
	 */
	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "2.5.0")
	static TitleComponentPart of(@NotNull Component component, Title.Times times){
		return title(component, times);
	}
	/**
	 * Creates a new title component part which contains fade in, stay and fade out times.
	 * @param component Text component
	 * @param times times
	 * @return new title component part
	 */
	static TitleComponentPart title(@NotNull Component component, Title.Times times){
		return new TitleComponentPartImpl(component, times.fadeIn(), times.stay(), times.fadeOut());
	}
	/**
	 * Creates a new title component part which contains fade in, stay and fade out times.
	 * @param component Text component
	 * @param times times
	 * @param hidePrefix true if the parser should hide the global prefix, else false
	 * @return new title component part
	 */
	static TitleComponentPart title(@NotNull Component component, Title.Times times, boolean hidePrefix){
		return new TitleComponentPartImpl(component, times.fadeIn(), times.stay(), times.fadeOut(), hidePrefix);
	}

	/**
	 * Creates a new title component part which contains fade in, stay and fade out times.
	 * @param component Text component
	 * @param in Fade in duration
	 * @param stay stay duration
	 * @param out Fade out duration
	 * @return new title component part
	 */
	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "2.5.0")
	static TitleComponentPart of(@NotNull Component component, @NotNull Duration in, @NotNull Duration stay, @NotNull Duration out){
		return title(component, in, stay, out);
	}
	/**
	 * Creates a new title component part which contains fade in, stay and fade out times.
	 * @param component Text component
	 * @param in Fade in duration
	 * @param stay stay duration
	 * @param out Fade out duration
	 * @return new title component part
	 */
	static TitleComponentPart title(@NotNull Component component, @NotNull Duration in, @NotNull Duration stay, @NotNull Duration out){
		return new TitleComponentPartImpl(component, in, stay, out, false);
	}
	/**
	 * Creates a new title component part which contains fade in, stay and fade out times.
	 * @param component Text component
	 * @param in Fade in duration
	 * @param stay stay duration
	 * @param out Fade out duration
	 * @return new title component part
	 */
	static TitleComponentPart title(@NotNull Component component, @NotNull Duration in, @NotNull Duration stay, @NotNull Duration out, boolean hidePrefix){
		return new TitleComponentPartImpl(component, in, stay, out, hidePrefix);
	}

	/**
	 * Creates a new component part which parses given string as a minimessage component.
	 * @param component minimessage component
	 * @return new component part
	 */
	static ComponentPart miniMessage(@NotNull String component) {
		return new ComponentPartImpl(MiniMessage.miniMessage().deserialize(component));
	}
	/**
	 * Creates a new component part which parses given string as a minimessage component.
	 * @param component minimessage component
	 * @param hidePrefix true if the prefix should be hidden on component parse, else false
	 * @return new component part
	 */
	static ComponentPart miniMessage(@NotNull String component, boolean hidePrefix) {
		return new ComponentPartImpl(MiniMessage.miniMessage().deserialize(component), hidePrefix);
	}

	/**
	 * Returns the text component which is displayed.
	 * @return text component
	 */
	Component getTextComponent();

	/**
	 * Returns true if the parser should disable the message prefix before parsing the message.
	 * @return true if the parser should hide else, false
	 */
	boolean isPrefixHidden();
}
