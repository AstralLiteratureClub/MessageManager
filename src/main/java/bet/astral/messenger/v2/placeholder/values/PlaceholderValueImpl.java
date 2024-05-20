package bet.astral.messenger.v2.placeholder.values;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

record PlaceholderValueImpl(Component value) implements PlaceholderValue {

	@Override
	public @NotNull Component getValue() {
		return value;
	}
}
