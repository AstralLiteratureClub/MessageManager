package bet.astral.messenger.placeholder;

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;

public interface PlaceholderJsonValue extends PlaceholderComponentValue{
	@NotNull
	default String asJson(){
		return GsonComponentSerializer.gson().serialize(asComponent());
	}
}
