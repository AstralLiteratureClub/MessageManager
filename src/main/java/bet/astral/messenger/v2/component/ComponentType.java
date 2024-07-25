package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.annotations.Immutable;
import bet.astral.messenger.v2.locale.source.components.TitleComponent;
import bet.astral.messenger.v2.receiver.Receiver;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;
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
	static ComponentType create(@NotNull String name, @NotNull ComponentTypeLoader loader, @NotNull ComponentForwarder componentForwarder, @NotNull String... aliases){
		return new ComponentTypeImpl(name, componentForwarder, loader, List.of(aliases));
	}


	/**
	 * Used for sending the bigger title to players
	 */
	@NotNull
	ComponentType TITLE = create("title", (basicComponent)->{
		if (basicComponent instanceof TitleComponent) {
			return ComponentPart.of(
					MiniMessage.miniMessage().deserialize(
							basicComponent.getComponent()),
					((TitleComponent) basicComponent).getFadeIn().asDuration(),
					((TitleComponent) basicComponent).getStay().asDuration(),
					((TitleComponent) basicComponent).getStay().asDuration());
		}
		return ComponentPart.miniMessage(basicComponent.getComponent());
	}, (receiver, component)->{
		receiver.sendTitlePart(TitlePart.TITLE, component.parsedComponent());
		if (component.parsedComponent() instanceof TitleComponentPart titleComponentPart) {
			receiver.sendTitlePart(TitlePart.TIMES, Title.Times.times(titleComponentPart.getFadeIn(), titleComponentPart.getStay(), titleComponentPart.getFadeOut()));
		}
	});
	/**
	 * Used for sending the smaller title to players
	 */
	@NotNull
	ComponentType SUBTITLE = create("subtitle", (basicComponent)->{
		if (basicComponent instanceof TitleComponent) {
			return ComponentPart.of(
					MiniMessage.miniMessage().deserialize(
							basicComponent.getComponent()),
					((TitleComponent) basicComponent).getFadeIn().asDuration(),
					((TitleComponent) basicComponent).getStay().asDuration(),
					((TitleComponent) basicComponent).getStay().asDuration());
		}
		return ComponentPart.miniMessage(basicComponent.getComponent());
	}, (receiver, component)->{
		receiver.sendTitlePart(TitlePart.SUBTITLE, component.parsedComponent());
		if (component.componentPart() instanceof TitleComponentPart titleComponentPart) {
			receiver.sendTitlePart(TitlePart.TIMES, Title.Times.times(titleComponentPart.getFadeIn(), titleComponentPart.getStay(), titleComponentPart.getFadeOut()));
		}
	}, "sub-title");
	/**
	 * Used for sending a message above action-bar
	 */
	@NotNull
	ComponentType ACTION_BAR = create("action-bar", (basicComponent)->ComponentPart
			.miniMessage(basicComponent.getComponent()), (receiver, component)-> receiver.sendActionBar(component.parsedComponent()), "actionbar");
	/**
	 * Used for sending a message to the chat box
	 */
	@NotNull
	ComponentType CHAT = create("chat", (basicComponent)->ComponentPart
			.miniMessage(basicComponent.getComponent()),
			(receiver, component)-> receiver.sendMessage(component.parsedComponent()));
	/**
	 * Used for changing the tab list header
	 */
	@NotNull
	ComponentType PLAYER_LIST_HEADER = create("list-header", (basicComponent)->ComponentPart
			.miniMessage(basicComponent.getComponent()),
			(receiver, componentPart) -> receiver.sendPlayerListHeader(componentPart.parsedComponent()), "player-list-header", "playerlistheader");
	/**
	 * Used for changing the tab list footer
	 */
	@NotNull
	ComponentType PLAYER_LIST_FOOTER = create("list-footer", (basicComponent)->ComponentPart
			.miniMessage(basicComponent.getComponent()),
			(receiver, componentPart) -> receiver.sendPlayerListFooter(componentPart.parsedComponent()), "player-list-footer", "playerlistfooter");

	/**
	 * The default component type registrar which is used by default in every messenger.
	 */
	ComponentTypeRegistry GLOBAL_COMPONENT_TYPE_REGISTRY = new ComponentTypeRegistry() {
		@Override
		protected void init() {
			register(CHAT);
			register(TITLE);
			register(SUBTITLE);
			register(ACTION_BAR);
			register(PLAYER_LIST_HEADER);
			register(PLAYER_LIST_FOOTER);
		}
	};


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

	/**
	 * Forwards given component part to the receiver.
	 * @param receiver receiver to receive component part
	 * @param componentPart component part
	 */
	void forward(@NotNull Receiver receiver, @NotNull ParsedComponentPart componentPart);

	/**
	 * Returns the component type loader
	 * @return component type loader
	 */
	@NotNull
	ComponentTypeLoader getLoader();

	/**
	 * Returns name and aliases combined in a list
	 * @return name and aliases
	 */
	default List<String> getNameAndAliases(){
		List<String> copy = new LinkedList<>(getAliases());
		copy.add(getName());
		return copy;
	}

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
		void send(@NotNull Receiver receiver, @NotNull ParsedComponentPart componentPart);
	}
}
