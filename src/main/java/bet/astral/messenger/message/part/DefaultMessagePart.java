package bet.astral.messenger.message.part;

import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.serializer.IMessageTypeSerializer;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class DefaultMessagePart<C> implements IMessagePart<C> {
	private final C value;
	@Getter
	private final IMessageTypeSerializer<C> serializer;
	private final MessageType type;
	public DefaultMessagePart(MessageType type, C value, IMessageTypeSerializer<C> serializer) {
		this.value = value;
		this.type = type;
		this.serializer = serializer;
	}
	public DefaultMessagePart(MessageType type, C value) {
		this.value = value;
		this.serializer = null;
		this.type = type;
	}

	@ApiStatus.Internal
	public DefaultMessagePart(MessageType type){
		this.value = null;
		this.serializer = null;
		this.type = type;
	}

	@Override
	public @NotNull MessageType getType() {
		return type;
	}

	@Override
	public C asComponent() {
		return value;
	}

	@Override
	public String asString() {
		if (serializer == null && value == null){
			return null;
		}
		return value != null ? serializer != null ? serializer.serialize(value) : value.toString() : null;
	}
}
