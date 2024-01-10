package bet.astral.messagemanager;

import bet.astral.messagemanager.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Message  {
	private final String key;
	protected final Map<Type, Component> messages;
	private final Map<String, Placeholder> builtInComponents;
	public boolean enabled = false;

	public Message(@NotNull String key, @NotNull Map<Type, Component> messages, @Nullable Map<String, Placeholder> builtInComponents) {
		this.key = key;
		this.messages = messages;
		this.builtInComponents = new HashMap<>(builtInComponents == null ? Map.of() : builtInComponents);
	}

	public Message(@NotNull String key, @NotNull Map<Type, Component> messages) {
		this.key = key;
		this.messages = messages;
		this.builtInComponents = Map.of();
	}

	public Message(@NotNull String key, @NotNull Component message) {
		this.key = key;
		this.messages = Map.of(Type.CHAT, message);
		this.builtInComponents = Map.of();
	}

	public Map<Type, Component> components() {
		return messages;
	}

	public Map<String, Placeholder> placeholders() {
		return builtInComponents;
	}

	/**
	 * Returns given the message as serialized mini message value
	 * @param type type
	 * @return value
	 */
	@Nullable
	public String stringValue(Type type) {
		if (messages.get(type) == null){
			return null;
		}
		return MiniMessage.miniMessage().serialize(messages.get(type));
	}

	@NotNull
	public String key() {
		return key;
	}

	@Nullable
	public Component componentValue(Type type) {
		if (messages.get(type) == null){
			return null;
		}
		return messages.get(type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, messages, builtInComponents);
	}


	public enum Type{
		CHAT,
		ACTION_BAR,
		TITLE,
		SUBTITLE;

		@Override
		public String toString() {
			return name();
		}
	}
}
