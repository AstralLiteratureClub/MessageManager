package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.platform.annotations.Immutable;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * Represents a component type which can be sent to a receiver.
 */
public interface ComponentType {
	/**
	 * Creates a new component type which can be registered to any command loader
	 * @param name name
	 * @param componentForwarder forwarder to forward messages
	 * @param aliases aliases
	 * @return new component type
	 */
	@NotNull
	static ComponentType create(@NotNull String name, @NotNull ComponentForwarder componentForwarder, @NotNull String... aliases){
		return new ComponentTypeImpl(name, componentForwarder, List.of(aliases));
	}

	/**
	 * Used for sending the bigger title to players
	 */
	@NotNull
	ComponentType TITLE = create("title", (receiver, component)->{
		receiver.sendTitlePart(TitlePart.TITLE, component.getTextComponent());
		if (component instanceof TitleComponentPart titleComponentPart) {
			receiver.sendTitlePart(TitlePart.TIMES, Title.Times.times(titleComponentPart.getFadeIn(), titleComponentPart.getStay(), titleComponentPart.getFadeOut()));
		}
	});
	/**
	 * Used for sending the smaller title to players
	 */
	@NotNull
	ComponentType SUBTITLE = create("subtitle", (receiver, component)->{
		receiver.sendTitlePart(TitlePart.SUBTITLE, component.getTextComponent());
		if (component instanceof TitleComponentPart titleComponentPart) {
			receiver.sendTitlePart(TitlePart.TIMES, Title.Times.times(titleComponentPart.getFadeIn(), titleComponentPart.getStay(), titleComponentPart.getFadeOut()));
		}
	}, "sub-title");
	/**
	 * Used for sending a message above action-bar
	 */
	@NotNull
	ComponentType ACTION_BAR = create("action-bar", (receiver, component)-> receiver.sendActionBar(component.getTextComponent()), "actionbar");
	/**
	 * Used for sending a message to the chat box
	 */
	@NotNull
	ComponentType CHAT = create("chat", (receiver, component)-> receiver.sendMessage(component.getTextComponent()));
	/**
	 * Used for changing the tab list header
	 */
	@NotNull
	ComponentType PLAYER_LIST_HEADER = create("list-header", (receiver, componentPart) -> receiver.sendPlayerListHeader(componentPart.getTextComponent()), "player-list-header", "playerlistheader");
	/**
	 * Used for changing the tab list footer
	 */
	@NotNull
	ComponentType PLAYER_LIST_FOOTER = create("list-footer", (receiver, componentPart) -> receiver.sendPlayerListFooter(componentPart.getTextComponent()), "player-list-footer", "playerlistfooter");

	/**
	 * Returns the name of this component type
	 * @return name
	 */
	@NotNull
	String getName();

	/**
	 * Returns the aliases for this component type
	 * @return aliases
	 */
	@NotNull
	@Immutable
	Collection<String> getAliases();

	void forward(@NotNull Receiver receiver, @NotNull ComponentPart componentPart);

	/**
	 * Forwards all components to the receiver
	 */
	@FunctionalInterface
	interface ComponentForwarder {
		/**
		 * Sends the given component part to the player
		 * @param receiver who receives the component part
		 * @param componentPart component part
		 */
		void send(@NotNull Receiver receiver, @NotNull ComponentPart componentPart);
	}
}
