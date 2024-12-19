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
import java.util.Objects;

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
	public Translation add(ComponentType componentType, Component component, boolean hidePrefix){
		messages.add(componentType, component, hidePrefix);
		return this;
	}
	public Translation add(ComponentType componentType, Component component, Title.Times times, boolean hidePrefix){
		messages.add(componentType, times, component, hidePrefix);
		return this;
	}

	public Translation add(ComponentType componentType, boolean hidePrefix, Component... components){
		messages.add(componentType, hidePrefix, components);
		return this;
	}
	public Translation add(ComponentType componentType, Title.Times times, boolean hidePrefix, Component... components){
		messages.add(componentType, times, hidePrefix, components);
		return this;
	}

	public Message getMessage() {
		return messages;
	}

	public static class Message {
		private final Map<ComponentType, ComponentPart> componentPart = new HashMap<>();
		private final Map<ComponentType, Boolean> disablePrefix = new HashMap<>();
		private final Translation translation;
		public Message(Translation translation){
			this.translation = translation;
		}

		public Message add(ComponentType componentType, Component component, boolean hidePrefix){
			componentPart.put(componentType, ComponentPart.of(component));
			disablePrefix.put(componentType, hidePrefix);
			return this;
		}
		public Message add(ComponentType componentType, Component component){
			return add(componentType, component, false);
		}
		public Message add(ComponentType componentType, Title.Times times, Component component){
			return add(componentType, times, component, false);
		}
		public Message add(ComponentType componentType, Title.Times times, Component component, boolean hidePrefix){
			if (times == null){
				componentPart.put(componentType, ComponentPart.of(component, hidePrefix));
				return this;
			}
			componentPart.put(componentType, ComponentPart.title(component, times, hidePrefix));
			return this;
		}
		public Message add(ComponentType componentType, boolean hidePrefix, Component... components){
			return add(componentType, null, hidePrefix, components);
		}
		public Message add(ComponentType componentType, Component... components){
			return add(componentType, null, false, components);
		}
		public Message add(ComponentType componentType, Title.Times times, Component... components){
			return add(componentType, times, false, components);
		}
		public Message add(ComponentType componentType, Title.Times times, boolean hidePrefix, Component... components) {
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
			disablePrefix.put(componentType, hidePrefix);
			if (times == null){
				componentPart.put(componentType, ComponentPart.of(finalComponent, hidePrefix));
				return this;
			}
			componentPart.put(componentType, ComponentPart.title(finalComponent, times, hidePrefix));
			return this;
		}

		public boolean useObject(){
			return componentPart.size()>1 || componentPart.get(ComponentType.CHAT)==null || disablePrefix.containsValue(true);
		}

		public Translation asTranslation(){
			return translation;
		}

		public Map<ComponentType, ComponentPart> getComponentParts() {
			return componentPart;
		}

		public Map<ComponentType, Boolean> getDisablePrefix() {
			return disablePrefix;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof TranslationKey that)) return false;
		return Objects.equals(getKey(), that.getKey());
	}

	@Override
	public int hashCode() {
		return 23*Objects.hash(getKey());
	}
}
