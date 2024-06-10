package bet.astral.messenger.v2.locale.source;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.locale.LanguageTable;
import bet.astral.messenger.v2.placeholder.loader.PlaceholderSource;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Loads and parses new base components for {@link LanguageTable}.
 */
public interface LanguageSource extends PlaceholderSource {
	/**
	 * Returns the messenger which this language source belongs to.
	 * @return messenger
	 */
	@NotNull Messenger getMessenger();
	/**
	 * Returns the translation key registry
	 * @return translation key registry
	 */
	@NotNull TranslationKeyRegistry getTranslationKeyRegistry();
	/**
	 * Returns the locale for this language source
	 * @return locale
	 */
	@NotNull
	Locale getLocale();

	/**
	 * Loads a single translation key, and it's base component. Returns null if no base component is found.
	 * @param translationKey translation key
	 * @return component base
	 */
	@NotNull
	CompletableFuture<@Nullable ComponentBase> loadComponent(@NotNull TranslationKey translationKey);

	/**
	 * Loads all given translation keys.
	 * Maps base components based on the translation key and puts loaded component
	 * if component was found else null as the loaded component to the map.
	 * @param translationKeys translation keys
	 * @return components
	 */
	@NotNull
	CompletableFuture<@NotNull Map<@NotNull TranslationKey, @Nullable ComponentBase>> loadAllComponents(@NotNull TranslationKey... translationKeys);

	/**
	 * Loads all given translation keys.
	 * Maps base components based on the translation key and puts loaded component
	 * if component was found else null as the loaded component to the map.
	 * @param translationKeys translation keys
	 * @return components
	 */
	@NotNull
	CompletableFuture<@NotNull Map<@NotNull TranslationKey, @Nullable ComponentBase>> loadAllComponents(@NotNull Collection<? extends TranslationKey> translationKeys);
}
