package bet.astral.messenger.v2.locale;

import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.component.ComponentBaseBuilder;
import bet.astral.messenger.v2.locale.source.LanguageSource;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import bet.astral.messenger.v2.annotations.Immutable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;

/**
 * Handles base components for given locale.
 */
public interface LanguageTable {
	/**
	 * Returns a new instance of language table based on language file registry and locale
	 * @param languageSource source
	 * @return language table
	 */
	@NotNull
	static LanguageTable of(@NotNull LanguageSource languageSource){
		return new LanguageTableImpl(languageSource.getTranslationKeyRegistry(), languageSource, languageSource.getLocale());
	}

	/**
	 * Returns the translation key registry
	 * @return translation key registry
	 */
	@NotNull TranslationKeyRegistry getTranslationkeyRegistry();

	/**
	 * Returns the locale used for this language table
	 * @return locale
	 */
	@NotNull
	Locale getLocale();

	/**
	 * Returns the fallback language which this language table defaults to.
	 * @return language table
	 */
	@NotNull
	Locale getFallbackLocale();

	/**
	 * Returns the fallback language table
	 * @return fallback table
	 */
	@NotNull
	LanguageTable getFallbackTable();

	/**
	 * Sets the fallback locale for this language table.
	 * @param locale fallback language
	 */
	void setFallbackLocale(@NotNull LanguageTable locale);

	/**
	 * Returns the language source for base components.
	 * @return source
	 */
	@NotNull
	LanguageSource getLanguageSource();

	/**
	 * Checks if there is a loaded component for the given translation key
	 * @param translationKey translation
	 * @return true if yes, false if no
	 */
	boolean exists(@NotNull TranslationKey translationKey);

	/**
	 * Checks if there is a fallback to the given translation key.
	 * @param translationKey translation
	 * @return true if yes, false if no
	 */
	boolean existsFallback(@NotNull TranslationKey translationKey);

	/**
	 * Returns given component base using the given translation key.
	 * Does not fall back to other languages if no message is found.
	 * @param key key
	 * @return component base
	 */
	@Nullable
	ComponentBase getComponent(@NotNull TranslationKey key);

	/**
	 * Returns given component base using the given translation key.
	 * Falls back to languages below this language table to provide the given translation key.
	 * If no component base is found returns null
	 * @param translationKey translation
	 * @return component base
	 */
	@Nullable
	ComponentBase getComponentFallBack(@NotNull TranslationKey translationKey);


	/**
	 * Returns all the cached base components in an immutable list.
	 * @return components
	 */
	@NotNull
	@Immutable
	Map<@NotNull TranslationKey, @NotNull ComponentBase> getBaseComponents();
	/**
	 * Adds given the base component to the cached base components with the given translations key.
	 * @param translationKey translation key
	 * @param componentBase component
	 */
	void addComponentBase(@NotNull TranslationKey translationKey, @NotNull ComponentBase componentBase);

	/**
	 * Adds given the base component to the cached base components with the given translations key.
	 * @param translationKey translation key
	 * @param componentBaseBuilder component
	 */
	default void addComponentBase(@NotNull TranslationKey translationKey, @NotNull ComponentBaseBuilder componentBaseBuilder) {
		this.addComponentBase(translationKey, componentBaseBuilder.build());
	}
}
