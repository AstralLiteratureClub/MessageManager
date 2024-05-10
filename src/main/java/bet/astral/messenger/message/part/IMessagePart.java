package bet.astral.messenger.message.part;

import bet.astral.messenger.message.MessageType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IMessagePart<C> {
	@NotNull
	MessageType getType();
	@Nullable
	C asComponent();
	@Nullable
	String asString();
}
