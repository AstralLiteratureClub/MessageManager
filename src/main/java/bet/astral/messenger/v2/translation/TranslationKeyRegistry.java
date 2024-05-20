package bet.astral.messenger.v2.translation;

import bet.astral.messenger.v2.annotations.UnsupportedAction;
import bet.astral.platform.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Translation key registry is used by message loaders and messenger to check if given translation exists.
 * Message loaders load all messages based on registered translation keys.
 */
public interface TranslationKeyRegistry {
	/**
	 * Creates a new translation key registry.
	 * @return registry
	 */
	static TranslationKeyRegistry create(){
		return new TranslationKeyRegistryImpl();
	}
	/**
	 * Returns all registered translation keys
	 * @return translation keys
	 */
	@NotNull
	@Immutable
	Set<@NotNull TranslationKey> getTranslationKeys();

	/**
	 * Returns true if given translation key is registered
	 * @param translationKey translation key
	 * @return true if registered, else false
	 */
	boolean isRegistered(@NotNull TranslationKey translationKey);
	/**
	 * Returns true if given translation key is registered
	 * @param key translation key
	 * @return true if registered, else false
	 */
	boolean isRegistered(@NotNull String key);
	/**
	 * Registers given the translation key to the registered translation keys
	 * @param translationKey translation key
	 */
	void register(@NotNull TranslationKey translationKey);

	/**
	 * Unregisters given the translation key from the registered translation keys.
	 * @param translationKey translation key
	 */
	@UnsupportedAction
	void unregister(@NotNull TranslationKey translationKey);
	/**
	 * Unregisters given the translation key from the registered translation keys.
	 * @param translationKey translation key
	 */
	@UnsupportedAction
	void unregister(@NotNull String translationKey);
}
