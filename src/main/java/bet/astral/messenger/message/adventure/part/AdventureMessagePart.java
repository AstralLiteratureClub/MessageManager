package bet.astral.messenger.message.adventure.part;

import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.part.DefaultMessagePart;
import bet.astral.messenger.message.serializer.IMessageTypeSerializer;
import com.mojang.brigadier.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class AdventureMessagePart extends DefaultMessagePart<Component> implements ComponentLike, Message {
	public AdventureMessagePart(MessageType type, Component value, IMessageTypeSerializer<Component> serializer) {
		super(type, value, serializer);
	}

	public AdventureMessagePart(MessageType type, Component value) {
		super(type, value);
	}

	public AdventureMessagePart(MessageType type) {
		super(type);
	}

	@Override
	public String getString() {
		Component component = asComponent();
		if (component == null){
			return "";
		}
		return PlainTextComponentSerializer.plainText().serialize(component);
	}
}
