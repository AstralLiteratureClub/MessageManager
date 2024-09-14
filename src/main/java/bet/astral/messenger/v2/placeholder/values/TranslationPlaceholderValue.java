package bet.astral.messenger.v2.placeholder.values;

import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.placeholder.collection.PlaceholderCollection;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.jetbrains.annotations.NotNull;

/**
 * Placeholder which is parsed using the receiver's locale and adds the parsed base component from the translation key
 */
public interface TranslationPlaceholderValue extends PlaceholderValue {
	/**
	 * Returns the component type, which is parsed from the translation key.
	 * @return component
	 */
	@NotNull
	ComponentType getValueComponentType();

	/**
	 * Returns the translation key which is used to the parse base component
	 * @return translation
	 */
	@NotNull
	TranslationKey getTranslationKey();

	/**
	 * Returns placeholders meant for this translation parse.
	 * @return placeholders
	 */
	@NotNull
	PlaceholderCollection getPlaceholders();
}
