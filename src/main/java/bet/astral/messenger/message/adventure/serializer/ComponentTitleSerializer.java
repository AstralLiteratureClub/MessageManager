package bet.astral.messenger.message.adventure.serializer;

import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.adventure.part.AdventureTitleMessagePart;
import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.message.serializer.IMessageTitleSerializer;
import bet.astral.messenger.message.serializer.IMessageTypeSerializer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

public class ComponentTitleSerializer<M extends IMessage<IMessagePart<Component>, Component>> implements IMessageTitleSerializer<M, Component> {
	private final IMessageTypeSerializer<Component> serializer;
	private final Class<M> type;
	public ComponentTitleSerializer(IMessageTypeSerializer<Component> serializer, Class<M> type) {
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
	public @NotNull IMessagePart<Component> createEmpty(@NotNull MessageType type) {
		return new AdventureTitleMessagePart(Component.empty(), Duration.ofMillis(0), Duration.ofMillis(0), Duration.ofMillis(0), type == MessageType.TITLE);
	}

	@Override
	public @NotNull IMessagePart<Component> deserialize(@NotNull List<String> serialized, @NotNull MessageType type) {
		return create(type, serializer.deserialize(serialized.get(0)));
	}

	@Override
	public @NotNull IMessagePart<Component> create(@NotNull MessageType type, @NotNull Component component, @NotNull Duration in, @NotNull Duration stay, @NotNull Duration out) {
		return new AdventureTitleMessagePart(component, in, stay, out, type == MessageType.TITLE);
	}

	@Override
	public @NotNull IMessagePart<Component> deserialize(@NotNull String deserialized, @NotNull MessageType type, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
		return create(type, serializer.deserialize(deserialized), fadeIn, stay, fadeOut);
	}

	@Override
	public @NotNull Component asComponent(@NotNull String serialized) {
		return serializer.deserialize(serialized);
	}
}
