package bet.astral.messenger.message.message;

import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.placeholder.Placeholder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class Message<C> extends AbstractMessage<C> implements IMessage<IMessagePart<C>, C> {
	protected final Map<MessageType, IMessagePart<C>> messages;

	public Message(@NotNull String key, @NotNull Map<MessageType, IMessagePart<C>> messages, @Nullable Map<String, Placeholder> placeholders) {
		super(key, placeholders);
		this.messages = messages;
	}

	public Message(@NotNull String key, @NotNull Map<MessageType, IMessagePart<C>> messages) {
		super(key, Collections.emptyMap());
		this.messages = messages;
	}

	public Message(@NotNull String key, @NotNull IMessagePart<C> message) {
		super(key, Collections.emptyMap());
		this.messages = Map.of(MessageType.CHAT, message);
	}

	public @NotNull Map<MessageType, IMessagePart<C>> components() {
		return messages;
	}


	/**
	 * Returns given the message as serialized mini message value
	 * @param type type
	 * @return value
	 */
	@Nullable
	public String asString(MessageType type) {
		return components().get(type).toString();
	}

	@Nullable
	public IMessagePart<C> message(MessageType type) {
		if (messages.get(type) == null){
			return null;
		}
		return messages.get(type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.key(), messages, enabled(), super.placeholders());
	}

	@Override
	public @NotNull Map<MessageType, IMessagePart<C>> parts() {
		return messages;
	}
}
