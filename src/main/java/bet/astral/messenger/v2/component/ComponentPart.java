package bet.astral.messenger.v2.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
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
	 * Creates a new title component part which contains fade in, stay and fade out times.
	 * @param component Text component
	 * @param times times
	 * @return new title component part
	 */
	static TitleComponentPart of(@NotNull Component component, Title.Times times){
		return new TitleComponentPartImpl(component, times.fadeIn(), times.stay(), times.fadeOut());
	}

	/**
	 * Creates a new title component part which contains fade in, stay and fade out times.
	 * @param component Text component
	 * @param in Fade in duration
	 * @param stay stay duration
	 * @param out Fade out duration
	 * @return new title component part
	 */
	static TitleComponentPart of(@NotNull Component component, @NotNull Duration in, @NotNull Duration stay, @NotNull Duration out){
		return new TitleComponentPartImpl(component, in, stay, out);
	}

	/**
	 * Creates a new component part which parses given string as a minimessage component.
	 * @param component minimessage component
	 * @return new component part
	 */
	static ComponentPart miniMessage(String component) {
		return new ComponentPartImpl(MiniMessage.miniMessage().deserialize(component));
	}

	/**
	 * Returns the text component which is displayed.
	 * @return text component
	 */
	Component getTextComponent();
}
