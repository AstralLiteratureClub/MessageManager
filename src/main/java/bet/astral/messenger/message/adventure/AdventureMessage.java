package bet.astral.messenger.message.adventure;

import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.message.Message;
import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
public class AdventureMessage extends Message<Component> implements IMessage<IMessagePart<Component>, Component> {
	public AdventureMessage(@NotNull String key, @NotNull Map<MessageType, IMessagePart<Component>> messages, @Nullable Map<String, Placeholder> placeholders) {
		super(key, messages, placeholders);
	}

	public AdventureMessage(@NotNull String key, @NotNull Map<MessageType, IMessagePart<Component>> messages) {
		super(key, messages);
	}

	public AdventureMessage(@NotNull String key, @NotNull IMessagePart<Component> message) {
		super(key, message);
	}


	@Override
	public Class<Component> getType() {
		return Component.class;
	}
}
