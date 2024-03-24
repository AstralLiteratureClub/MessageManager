package bet.astral.messenger.message.serializer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IMessageTypeSerializer<C> {
	@NotNull
	String serialize(@NotNull C message);
	@NotNull
	C deserialize(@NotNull String s);
	@NotNull
	C combine(@NotNull List<C> messages);
}
