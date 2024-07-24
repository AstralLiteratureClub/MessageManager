package bet.astral.messenger.v2.cloud.placeholders;

import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a custom variable placeholder value. Overrides {@link #toPlaceholder(String)} method to return {@link VariablePlaceholder}
 */
public interface VariablePlaceholderValue extends PlaceholderValue {
	/**
	 * Returns a new caption variable placeholder using given key and this as the value.
	 * @param key key
	 * @return value
	 */
	@Override
	default @NotNull VariablePlaceholder toPlaceholder(@NotNull String key) {
		return VariablePlaceholder.of(key, this);
	}
}
