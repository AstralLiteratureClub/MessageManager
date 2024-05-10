package bet.astral.messenger.message.serializer;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IMessageTypeSerializer<C> {
	@NotNull
	String serialize(@NotNull C message);
	@NotNull
	C deserialize(@NotNull String s);
	@NotNull
	C combine(@NotNull List<C> messages);
	@NotNull
	C appendSpace(@NotNull C component);
	@NotNull
	C appendNewLine(@NotNull C component);
}
