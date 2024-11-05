package bet.astral.messenger.v3.cloud.placeholders;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.collection.PlaceholderList;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import bet.astral.messenger.v2.placeholder.values.RandomPlaceholderValue;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.minecraft.extras.caption.RichVariable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Cloud variable to link Messenger and Cloud.
 * Extends {@link Placeholder} and {@link RichVariable} to give a better linkage between {@link CommandManager} and {@link Messenger}
 */
@SuppressWarnings("unused")
public interface VariablePlaceholder extends Placeholder, RichVariable, VariablePlaceholderValue, CaptionVariable {
	/**
	 * Returns a new placeholder reference which extends variable placeholder
	 * @param placeholder placeholder
	 * @return variable placeholder
	 */
	static @NotNull VariablePlaceholder reference(@NotNull Placeholder placeholder){
		return new VariablePlaceholderReferenceImpl(placeholder);
	}
	/**
	 * Returns a new placeholder reference which extends variable placeholder
	 * @param placeholders placeholders
	 * @return variable placeholder
	 */
	static @NotNull VariablePlaceholder[] reference(@NotNull Placeholder... placeholders){
		PlaceholderList placeholderList = new PlaceholderList();
		for (@NotNull Placeholder placeholder : placeholders) {
			placeholderList.add(reference(placeholder));
		}
		return placeholderList.stream().map(p->(VariablePlaceholder) p).toArray(VariablePlaceholder[]::new);
	}


	/**
	 * Converts given placeholder to a variable placeholder.
	 * Checks if placeholder is a random placeholder and returns a random and returns random placeholder placeholder.
	 * Returns given placeholder back if given placeholder is a variable placeholder.
	 * @param placeholder placeholder
	 * @return variable placeholder
	 */
	static @NotNull VariablePlaceholder clone(@NotNull Placeholder placeholder){
		if (placeholder instanceof VariablePlaceholder variablePlaceholder){
			return variablePlaceholder;
		}
		if (placeholder instanceof RandomPlaceholderValue randomPlaceholderValue){
			return new RandomVariablePlaceholderImpl(placeholder.getKey(), randomPlaceholderValue);
		}
		return new VariablePlaceholderImpl(placeholder.getKey(), placeholder.getValue());
	}
	/**
	 * Returns a new {@link VariablePlaceholder} of given caption variable.
	 * @param captionVariable caption variable
	 * @return new placeholder
	 */
	@Contract("_ -> new")
	static @NotNull VariablePlaceholder of(@NotNull RichVariable captionVariable){
		return new VariablePlaceholderImpl(captionVariable.key(), captionVariable.asComponent());
	}
	/**
	 * Returns a new {@link VariablePlaceholder} of given caption variable.
	 * @param captionVariable caption variable
	 * @return new placeholder
	 */
	@Contract("_ -> new")
	static @NotNull VariablePlaceholder of(@NotNull CaptionVariable captionVariable){
		if (captionVariable instanceof RichVariable richVariable){
			return of(richVariable);
		}
		return new VariablePlaceholderImpl(captionVariable.key(), captionVariable.value());
	}

	/**
	 * Returns a new placeholder reference which extends variable placeholder
	 * @param variables placeholders
	 * @return variable placeholder
	 */
	static @NotNull VariablePlaceholder[] of(@NotNull CaptionVariable... variables){
		PlaceholderList placeholderList = new PlaceholderList();
		for (@NotNull CaptionVariable placeholder : variables) {
			placeholderList.add(of(placeholder));
		}
		return placeholderList.stream().map(p->(VariablePlaceholder) p).toArray(VariablePlaceholder[]::new);
	}


	/**
	 * Returns a new caption variable using given key and given value.
	 * @param key key
	 * @param component value
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	static @NotNull VariablePlaceholder of(@NotNull String key, @NotNull Component component){
		return new VariablePlaceholderImpl(key, component);
	}

	/**
	 * Returns a new caption variable using given the key and given value
	 * @param key key
	 * @param placeholderValue value
	 * @return new variable
	 */
	@Contract("_, _ -> new")
	static @NotNull VariablePlaceholder of(@NotNull String key, @NotNull PlaceholderValue placeholderValue){
		return new VariablePlaceholderImpl(key, placeholderValue);
	}
	/**
	 * Returns a new variable placeholder using legacy parsing.
	 * @param key key
	 * @param legacyString legacy string
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	static @NotNull Placeholder legacy(@NotNull String key, @NotNull String legacyString) {
		return new VariablePlaceholderImpl(key, LegacyComponentSerializer.legacySection().deserialize(legacyString));
	}

	/**
	 * Returns a new variable placeholder using plain text parsing.
	 * @param key key
	 * @param string plain serializer
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	static @NotNull Placeholder plain(@NotNull String key, @NotNull String string) {
		return new VariablePlaceholderImpl(key, Component.text(string));
	}

	/**
	 * Returns a new random variable placeholder.
	 * @param key key
	 * @param objects values
 	 * @return new random placeholder
	 */
	@Contract("_, _ -> new")
	static @NotNull VariablePlaceholder random(@NotNull String key, @NotNull Object... objects) {
		return random(key, List.of(objects));
	}

	/**
	 * Returns a new random placeholder placeholder.
	 * @param key key
	 * @param objects values
	 * @return new random placeholder
	 */
	@Contract("_, _ -> new")
	static @NotNull VariablePlaceholder random(@NotNull String key, @NotNull List<Object> objects){
		return new RandomVariablePlaceholderImpl(key, objects);
	}
	@Override
	@NotNull
	default Component asComponent() {
		return getValue();
	}
	default @NotNull Component component(){
		return getValue();
	}

	@Override
	@NonNull
	default String key() {
		return getKey();
	}

	@Override
	@NonNull
	default String value() {
		return getValuePlain();
	}

	@Override
	@NotNull
	default VariablePlaceholder toPlaceholder(@NotNull String key) {
		return VariablePlaceholderValue.super.toPlaceholder(key);
	}
}
