package bet.astral.messenger.v2.placeholder.values;

import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.collection.PlaceholderCollection;
import bet.astral.messenger.v2.placeholder.collection.PlaceholderList;
import bet.astral.messenger.v2.translation.TranslationKey;
import com.google.gson.JsonElement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a placeholder value which may be converted to a placeholder easily.
 */
public interface PlaceholderValue extends ComponentLike, Cloneable {
	/**
	 * Creates a new placeholder value with given component value
	 * @param value value
	 * @return new placeholder value
	 */
	static PlaceholderValue of(ComponentLike value){
		return new PlaceholderValueImpl(value.asComponent());
	}

	/**
	 * Creates a new placeholder value with the given legacy string and converts it to a legacy component.
	 * @param legacyString The legacy component string
	 * @return new placeholder value
	 */
	static PlaceholderValue legacy(@NotNull String legacyString){
		return new PlaceholderValueImpl(LegacyComponentSerializer.legacyAmpersand().deserialize(legacyString));
	}

	/**
	 * Creates a new placeholder with the given plain text string converted to a plain component
	 * @param plainString plain string
	 * @return new placeholder value
	 */
	static PlaceholderValue plain(@NotNull String plainString){
		return new PlaceholderValueImpl(Component.text(plainString));
	}

	/**
	 * Creates a new placeholder with the given translation.
	 * @param translationKey translation
	 * @param type type
	 * @param placeholders placeholders
	 * @return value
	 */
	static PlaceholderValue translation(@NotNull TranslationKey translationKey, @NotNull ComponentType type, Placeholder... placeholders){
		if (placeholders==null||placeholders.length==0) {
			return new TranslationPlaceholderValueImpl(translationKey, type, new PlaceholderList());
		} else {
			return new TranslationPlaceholderValueImpl(translationKey, type, PlaceholderCollection.list(placeholders));
		}
	}
	/**
	 * Creates a new placeholder with the given translation.
	 * @param translationKey translation
	 * @param type type
	 * @param placeholders placeholders
	 * @return value
	 */
	static PlaceholderValue translation(@NotNull TranslationKey translationKey, @NotNull ComponentType type, @NotNull PlaceholderCollection placeholders){
		return new TranslationPlaceholderValueImpl(translationKey, type, placeholders);
	}

	/**
	 * Returns a placeholder with given key.
	 * Creates a placeholder which has this placeholder value stored in a field
	 * and executes {@link #getValue()} each time {@link Placeholder#getValue()} is used.
	 * @param key key
	 * @return placeholder
	 */
	@NotNull
	default Placeholder toPlaceholder(@NotNull String key) {
		return Placeholder.of(key, this);
	}


	/**
	 * Returns THE value of the placeholder value.
	 * @return value
	 */
	@NotNull
	Component getValue();

	/**
	 * Returns {@link #getValue()}
	 * @return value
	 */
	@Override
	default @NotNull Component asComponent() {
		return getValue();
	}

	/**
	 * Returns the component value as a json string
	 * @return value as json
	 */
	@NotNull
	default String getValueJson() {
		return GsonComponentSerializer.gson().serialize(getValue());
	}

	/**
	 * Returns the component value as a gson element
	 * @return value as json element
	 */
	@NotNull
	default JsonElement getValueJsonElement() {
		return GsonComponentSerializer.gson().serializeToTree(getValue());
	}

	/**
	 * Returns the component value as legacy chat formatting
	 * @return value as legacy chat formatting
	 */
	@NotNull
	default String getValueLegacy() {
		return LegacyComponentSerializer.legacyAmpersand().serialize(getValue());
	}
	/**
	 * Returns the component value as legacy chat formatting
	 * @return value as legacy chat formatting
	 */
	@NotNull
	default String getValueLegacySection() {
		return LegacyComponentSerializer.legacySection().serialize(getValue());
	}
	/**
	 * Returns the component value as plain text without formatting
	 * @return plain text value
	 */
	@NotNull
	default String getValuePlain() {
		return PlainTextComponentSerializer.plainText().serialize(getValue());
	}
}
