package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.platform.annotations.Immutable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * Represents a base component message which can send multiple types of messages
 */
public interface ComponentBase {
	/**
	 * Returns the translation key for this base component base
	 * @return key
	 */
	@NotNull
	TranslationKey getTranslationKey();

	/**
	 * Returns the parts for given locale if the locale has been parsed.
	 * @param locale locale
	 * @return parts, else null
	 */
	@Nullable
	@Immutable
	Map<@NotNull ComponentType, @NotNull ComponentPart> getParts(Locale locale);

	/**
	 * Returns all possible locales for this message
	 * @return loaded locales
	 */
	@Immutable
	@NotNull Collection<@NotNull Locale> getLocales();

	/**
	 * Returns the placeholders per each locale
	 * @param locale locale
	 * @return placeholders
	 */
	@Nullable
	@Immutable
	Map<String, Placeholder> getPlaceholders(@NotNull Locale locale);

	/**
	 * Returns if the given locale is disabled with their message parts
	 * @param locale locale
	 * @return true if disabled, else false
	 */
	boolean isDisabled(@NotNull Locale locale);

	/**
	 * Disables/Enables given locale
	 * @param locale locale to disable/enable
	 * @param disable true if disable, false if enable
	 */
	void setDisabled(@NotNull Locale locale, boolean disable);

	/**
	 * Adds given locale to the locales possible. If the component part-map is empty, the locale will not be added
	 * @param locale locale
	 * @param parts parts
	 */
	void addLocale(@NotNull Locale locale, @NotNull Map<@NotNull ComponentType, @NotNull ComponentPart> parts);

	/**
	 * Removes given locale from the base component.
	 * @param locale locale
	 */
	void removeLocale(@NotNull Locale locale);
}
