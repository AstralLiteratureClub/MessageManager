package bet.astral.messenger.message.message;

import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.placeholder.Placeholder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface IMessage<Part extends IMessagePart<C>, C> {
	@NotNull
	Map<MessageType, Part> parts();
	@NotNull
	Map<String, Placeholder> placeholders();
	@NotNull
	String key();
	Class<C> getType();
	boolean enabled();
	void setEnabled(boolean v);
}
