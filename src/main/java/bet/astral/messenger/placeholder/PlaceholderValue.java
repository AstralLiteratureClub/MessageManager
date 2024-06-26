package bet.astral.messenger.placeholder;

import org.jetbrains.annotations.NotNull;

public interface PlaceholderValue {
	@NotNull
	default Placeholder toPlaceholder(@NotNull String prefix){
		return new Placeholder(prefix, getValue());
	}
	@NotNull
	String getValue();
}
