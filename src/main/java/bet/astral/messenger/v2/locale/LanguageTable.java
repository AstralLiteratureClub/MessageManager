package bet.astral.messenger.v2.locale;

import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.component.ComponentBaseBuilder;
import bet.astral.messenger.v2.locale.source.LanguageSource;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import bet.astral.platform.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

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
	 * Returns the language source for base components.
	 * @return source
	 */
	@NotNull
	LanguageSource getLanguageSource();


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
