package bet.astral.messenger.placeholder;

import bet.astral.messenger.message.part.IMessagePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MessagePlaceholder extends Placeholder{
	private final IMessagePart<Component> messagePart;
	public MessagePlaceholder(@NotNull String key, @NotNull IMessagePart<Component> messagePart) {
		super(key, true);
		this.messagePart = messagePart;
	}

	@Override
	public @NotNull Component asComponent() {
		if (messagePart == null || messagePart.asComponent() == null) {
			return Component.text("%"+key()+"%");
		}
		return Objects.requireNonNull(messagePart.asComponent());
	}

	@Override
	public String stringValue() {
		return MiniMessage.miniMessage().serialize(asComponent());
	}
}
