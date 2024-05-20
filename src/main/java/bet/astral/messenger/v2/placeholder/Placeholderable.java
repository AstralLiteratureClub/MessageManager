package bet.astral.messenger.v2.placeholder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Placeholderable {
	/**
	 * Creates a new collection of placeholders using the given string as the prefix.
	 * @param prefix prefix
	 * @return new placeholder collection
	 */
	@NotNull
	Collection<@NotNull Placeholder> toPlaceholders(@Nullable String prefix);

	/**
	 * Creates a new collection of placeholders without a prefix.
	 * @return new placeholder collection
	 */
	@NotNull
	default Collection<@NotNull Placeholder> toPlaceholders() {
		return toPlaceholders(null);
	}
}
