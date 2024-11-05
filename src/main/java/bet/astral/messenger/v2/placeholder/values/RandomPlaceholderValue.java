package bet.astral.messenger.v2.placeholder.values;

import bet.astral.messenger.v2.utils.Randomly;
import bet.astral.messenger.v2.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A random placeholder value returns a random value each time {@link #getPossibleValues()} is used.
 */
public interface RandomPlaceholderValue extends PlaceholderValue, Randomly {
	/**
	 * Returns all possible values from this placeholder
	 * @return possible values
	 */
	@NotNull
	List<Component> getPossibleValues();

	/**
	 * Returns a random placeholder using given placeholders.
	 * Returns a random placeholder because the placeholder value is random..!
	 * @param key key
	 * @return new random placeholder
	 */
	@Override
	default @NotNull Placeholder toPlaceholder(@NotNull String key) {
		return Placeholder.random(key, this);
	}
}
