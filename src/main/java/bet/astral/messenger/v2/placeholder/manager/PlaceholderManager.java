package bet.astral.messenger.v2.placeholder.manager;

import bet.astral.messenger.v2.placeholder.Placeholder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

/**
 * Placeholder manager placeholders are always called when a message is being parsed.
 * V1.1 used similar setup in the messenger class,
 * but splitting it to a different class allows usage of multiple placeholder managers for multiple different languages
 */
public interface PlaceholderManager {
	/**
	 * Creates a new placeholder manager implementation
	 * @return new instance
	 */
	static PlaceholderManager create() {
		return new PlaceholderManagerImpl();
	}

	/**
	 * Returns immutable map of all the placeholders cached in this placeholder manager
	 * @return immutable copy of placeholders
	 */
	@NotNull
	Map<String, Placeholder> getGlobalPlaceholders();

	/**
	 * Overrides, or writes a placeholder with given placeholders name to the cache.
	 * @param placeholder placeholder
	 * @return self
	 */
	@Contract("_, -> this")
	PlaceholderManager overridePlaceholder(@NotNull Placeholder placeholder);

	/**
	 * Overrides, or writes given placeholders to the placeholder cache.
	 * @param placeholders placeholders
	 * @return self
	 */
	@Contract("_, -> this")
	PlaceholderManager overridePlaceholders(@NotNull Placeholder... placeholders);
	/**
	 * Overrides, or writes given placeholders to the placeholder cache.
	 * @param placeholders placeholders
	 * @return self
	 */
	@Contract("_, -> this")
	PlaceholderManager overridePlaceholders(@NotNull Collection<? extends @NotNull Placeholder> placeholders);
	/**
	 * Overrides all placeholders in the cache and uses given placeholder map's values in the cache
	 * @param placeholders placeholders
	 * @return self
	 */
	@Contract("_, -> this")
	PlaceholderManager overrideAllPlaceholders(@NotNull Map<@NotNull String, @NotNull Placeholder> placeholders);
}
