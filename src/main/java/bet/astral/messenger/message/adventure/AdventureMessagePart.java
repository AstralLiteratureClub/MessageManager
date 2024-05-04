package bet.astral.messenger.message.adventure;

import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.part.DefaultMessagePart;
import bet.astral.messenger.message.serializer.IMessageTypeSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

public class AdventureMessagePart extends DefaultMessagePart<ComponentLike> {
	public AdventureMessagePart(MessageType type, ComponentLike value, IMessageTypeSerializer<ComponentLike> serializer) {
		super(type, value, serializer);
	}

	public AdventureMessagePart(MessageType type, ComponentLike value) {
		super(type, value);
	}

	public AdventureMessagePart(MessageType type) {
		super(type);
	}
	public AdventureMessagePart(MessageType type, Component value, IMessageTypeSerializer<ComponentLike> serializer) {
		super(type, value, serializer);
	}

	public AdventureMessagePart(MessageType type, Component value) {
		super(type, value);
	}
}
