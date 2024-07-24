package bet.astral.messenger.v2.placeholder.hooks;

import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Placeholder hooks work similar to PAPI's placeholder expansions.
 * The hook handle method is given the receiver and the key,
 * and if there is no placeholder for the given key, the return value is null.
 */
public interface PlaceholderHook {
	/**
	 * Returns the prefix of the placeholder, for example, player -> player_health
	 * @return prefix
	 */
	@NotNull
	String getName();

	/**
	 * Returns the author(s) of the placeholder expansion
	 * @return author(s)
	 */
	@NotNull
	String getAuthor();

	/**
	 * Returns the website to download and report bugs about the expansion
	 * @return website url
	 */
	@NotNull
	String getWebsite();

	/**
	 * Returns the version of the placeholder hook.
	 * @return hook version
	 */
	@NotNull
	String getVersion();

	/**
	 * Parses placeholder from the given key. Returns null if no placeholder is found for the given key.
	 * @param receiver who is receiving this message
	 * @param key key
	 * @return placeholder, else null
	 */
	@Nullable
	PlaceholderValue handle(@NotNull Object receiver, String key);

	default void register(@NotNull PlaceholderHookManager hookManager) {
		hookManager.register(this);
	}
	default void unregister(@NotNull PlaceholderHookManager hookManager) {
		hookManager.unregister(this);
	}
}
