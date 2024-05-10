package bet.astral.messenger.placeholder;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public interface PlaceholderLegacyValue extends PlaceholderValue {
	@Override
	@NotNull
	default Placeholder toPlaceholder(@NotNull String prefix) {
		if (this instanceof PlaceholderComponentValue placeholderComponentValue){
			return new Placeholder(prefix, placeholderComponentValue.asComponent());
		}
		return new Placeholder(prefix, asLegacy(), true);
	}

	@NotNull
	default String asLegacy(){
		if (this instanceof PlaceholderComponentValue value){
			return LegacyComponentSerializer.legacyAmpersand().serialize(value.asComponent());
		}
		return getValue();
	}
}
