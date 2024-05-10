package bet.astral.messenger.message.serializer;

import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.MessageType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IMessageSerializer<M extends IMessage<P, C>, P extends IMessagePart<C>, C> {
	/**
	 * Name of this parser
	 * @return parser name
	 */
	@NotNull
	String name();
	@NotNull
	Class<M> messageType();
	@NotNull
	P createEmpty(@NotNull MessageType type);
	@NotNull
	P create(@NotNull MessageType type, @NotNull C value);
	@NotNull
	P deserialize(@NotNull String serialized, @NotNull MessageType type);
	@NotNull
	P deserialize(@NotNull List<String> serialized, @NotNull MessageType type);

}
