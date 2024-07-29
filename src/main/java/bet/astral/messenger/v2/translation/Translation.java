package bet.astral.messenger.v2.translation;

import bet.astral.messenger.v2.component.ComponentPart;
import bet.astral.messenger.v2.component.ComponentType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Translation implements TranslationKey {
	private final String key;
	private final Message messages;

	public Translation(String key) {
		this.key = key;
		this.messages = new Message(this);
	}

	public static Component text(@NotNull String val){
		return MiniMessage.miniMessage().deserialize(val);
	}
	public static Component legacy(@NotNull String val){
		return LegacyComponentSerializer.legacyAmpersand().deserialize(val);
	}

	@Override
	public @NotNull String getKey() {
		return key;
	}

	public Translation add(ComponentType componentType, Component component){
		messages.add(componentType, component);
		return this;
	}
	public Translation add(ComponentType componentType, Component component, Title.Times times){
		messages.add(componentType, times, component);
		return this;
	}

	public Translation add(ComponentType componentType, Component... components){
		messages.add(componentType, components);
		return this;
	}
	public Translation add(ComponentType componentType, Title.Times times, Component... components){
		messages.add(componentType, times, components);
		return this;
	}

	public Message getMessage() {
		return messages;
	}

	public static class Message {
		private final Map<ComponentType, ComponentPart> componentPart = new HashMap<>();
		private final Translation translation;
		public Message(Translation translation){
			this.translation = translation;
		}

		public Message add(ComponentType componentType, Component component){
			componentPart.put(componentType, ComponentPart.of(component));
			return this;
		}
		public Message add(ComponentType componentType, Title.Times times, Component component){
			if (times == null){
				componentPart.put(componentType, ComponentPart.of(component));
				return this;
			}
			componentPart.put(componentType, ComponentPart.of(component, times));
			return this;
		}
		public Message add(ComponentType componentType, Component... components){
			return add(componentType, null, components);
		}
		public Message add(ComponentType componentType, Title.Times times, Component... components){
			Component finalComponent = null;
			for (Component component : components){
				if (finalComponent== null){
					finalComponent = component;
				} else {
					finalComponent = finalComponent.append(component);
				}
			}
			if (finalComponent==null){
				return this;
			}
			if (times == null){
				componentPart.put(componentType, ComponentPart.of(finalComponent));
				return this;
			}
			componentPart.put(componentType, ComponentPart.of(finalComponent, times));
			return this;
		}

		public boolean useObject(){
			return componentPart.size()>1 || componentPart.get(ComponentType.CHAT)==null;
		}

		public Translation asTranslation(){
			return translation;
		}

		public Map<ComponentType, ComponentPart> getComponentParts() {
			return componentPart;
		}
	}
}
