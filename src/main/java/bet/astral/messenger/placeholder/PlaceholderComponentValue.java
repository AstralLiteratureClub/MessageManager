package bet.astral.messenger.placeholder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;

public interface PlaceholderComponentValue extends PlaceholderValue, ComponentLike {
	@NotNull
	@Override
	default Placeholder toPlaceholder(@NotNull String prefix){
		return new Placeholder(prefix, asComponent());
	}

	@NotNull
	default String getValue(){
		return PlainTextComponentSerializer.plainText().serialize(asComponent());
	}
	@Override
	@NotNull
	Component asComponent();
}
