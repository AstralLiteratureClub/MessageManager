package bet.astral.messenger.message.serializer;

import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.part.DefaultMessagePart;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DefaultMessageSerializer<M extends IMessage<DefaultMessagePart<C>, C>, C> implements IMessageSerializer<M, DefaultMessagePart<C>, C> {
	private final IMessageTypeSerializer<C> serializer;
	private final Class<M> type;
	public DefaultMessageSerializer(IMessageTypeSerializer<C> serializer, Class<M> type) {
		this.serializer = serializer;
		this.type = type;
	}

	@Override
	public @NotNull String name() {
		return "default";
	}

	@Override
	public @NotNull Class<M> messageType() {
		return type;
	}

	@Override
	public @NotNull DefaultMessagePart<C> createEmpty(@NotNull MessageType type) {
		return new DefaultMessagePart<>(type);
	}

	@Override
	public @NotNull DefaultMessagePart<C> create(@NotNull MessageType type, @NotNull C value) {
		return new DefaultMessagePart<>(type, value, serializer);
	}

	@Override
	public @NotNull DefaultMessagePart<C> deserialize(@NotNull String serialized, @NotNull MessageType type) {
		return create(type, serializer.deserialize(serialized));
	}

	@Override
	public @NotNull DefaultMessagePart<C> deserialize(@NotNull List<String> serialized, @NotNull MessageType type) {
		return create(type, serializer.combine(serialized.stream().map(serializer::deserialize).toList()));
	}
}
