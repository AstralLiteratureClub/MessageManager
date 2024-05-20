package bet.astral.messenger.v2.translation;

import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a message key
 */
public interface TranslationKey extends Translatable {
	/**
	 * Creates a new translation based on the key given
	 * @param key key
	 * @return new translation
	 */
	@NotNull
	static TranslationKey of(@NotNull String key) {
		return new TranslationKeyImpl(key);
	}

	/**
	 * Creates a new translation based on the translation key from the the given translatable
	 * @param translatable translatable
	 * @return translation
	 */
	@NotNull
	static TranslationKey of(@NotNull Translatable translatable){
		return new TranslationKeyImpl(translatable.translationKey());
	}

	/**
	 * Returns the translation key for this translation
	 * @return key
	 */
	@NotNull String getKey();

	@Override
	default @NotNull String translationKey() {
		return getKey();
	}
}
