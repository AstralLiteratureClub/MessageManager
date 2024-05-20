package bet.astral.messenger.v2.translation;

import org.jetbrains.annotations.NotNull;

/**
 * Provides a translation key.
 */
public interface TranslationKeyProvider {
	/**
	 * Returns translation key used for messenger
	 * @return translation key
	 */
	@NotNull
	TranslationKey getTranslationKey();
}
