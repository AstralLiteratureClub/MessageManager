package bet.astral.messenger.v2.placeholder;

import bet.astral.messenger.v2.utils.CloneableWithName;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.placeholder.collection.PlaceholderCollection;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import bet.astral.messenger.v2.placeholder.values.RandomPlaceholderValue;
import bet.astral.messenger.v2.translation.TranslationKey;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.time.Instant;
import java.util.*;

/**
 * A variable or a placeholder for a message's placeholders.
 * Placeholders can be implemented in many ways, but a common way to implement placeholders is just component and key
 */
public interface Placeholder extends ComponentLike, CloneableWithName, PlaceholderValue {
	/**
	 * Creates a new placeholder using the given component
	 *
	 * @param key       key
	 * @param component component value
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	@NotNull
	static Placeholder of(@NotNull String key, @NotNull ComponentLike component) {
		return new PlaceholderImpl(key, component.asComponent());
	}

	/**
	 * Creates a new placeholder using the given placeholder value.
	 *
	 * @param key              key
	 * @param placeholderValue value
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	@NotNull
	static Placeholder of(@NotNull String key, @NotNull PlaceholderValue placeholderValue) {
		return new PlaceholderImpl(key, placeholderValue);
	}
	/**
	 * Creates a new placeholder using the given number.
	 *
	 * @param key              key
	 * @param number number
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	@NotNull
	static Placeholder of(@NotNull String key, @NotNull Number number) {
		return new PlaceholderImpl(key, Component.text(number.toString()));
	}

	/**
	 * Creates a new placeholder using the given number.
	 *
	 * @param key              key
	 * @param string string
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	@NotNull
	static Placeholder of(@NotNull String key, @NotNull String string) {
		return new PlaceholderImpl(key, Component.text(string));
	}


	/**
	 * Creates a placeholder where prefix can be null and does not append PREFIX_ in front of the key
	 * @param prefix prefix, nullable
	 * @param key key, not null
	 * @param componentLike value, not null
	 * @return new placeholder
	 */
	@Contract("_, _, _ -> new")
	@NotNull
	static Placeholder of(@Nullable String prefix, @NotNull String key, @NotNull ComponentLike componentLike){
		return new PlaceholderImpl(prefix != null ? prefix+"_"+key : key, componentLike.asComponent());
	}

	/**
	 * Returns a new placeholder using the given legacy text and parses it using {@link LegacyComponentSerializer#deserialize(String)}
	 *
	 * @param key key
	 * @param legacyString legacy value
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	@NotNull
	static Placeholder legacy(@NotNull String key, @NotNull String legacyString) {
		return new PlaceholderImpl(key, LegacyComponentSerializer.legacySection().deserialize(legacyString));
	}

	/**
	 * Returns a placeholder which has string parsed using {@link Component#text(String)}.
	 *
     * @param key key
	 * @param string string
	 * @return new placeholder
	 */
	@Contract("_, _ -> new")
	@NotNull
	static Placeholder plain(@NotNull String key, @NotNull String string) {
		return new PlaceholderImpl(key, Component.text(string));
	}

	/**
	 * Creates a new random placeholder using the components given
	 *
	 * @param key    key
	 * @param values possible values
	 * @return new random placeholder
	 */
	@Contract("_, _ -> new")
	static @NotNull Placeholder random(@NotNull String key, @NotNull Collection<ComponentLike> values) {
		return new RandomPlaceholderImpl(key, values);
	}

	/**
	 * Creates a new random placeholder using the components given
	 *
	 * @param key    key
	 * @param values possible values
	 * @return new random placeholder
	 */
	@Contract("_, _ -> new")
	static @NotNull Placeholder random(@NotNull String key, @NotNull ComponentLike... values) {
		Collection<ComponentLike> componentLikes = new LinkedList<>(Arrays.asList(values));
		return new RandomPlaceholderImpl(key, componentLikes);
	}

	/**
	 * Creates a new random placeholder using {@link RandomPlaceholderValue#getPossibleValues()}
	 *
	 * @param key                    key
	 * @param randomPlaceholderValue possible values
	 * @return key
	 */
	@Contract("_, _ -> new")
	static @NotNull Placeholder random(@NotNull String key, @NotNull RandomPlaceholderValue randomPlaceholderValue) {
		return new RandomPlaceholderImpl(key, randomPlaceholderValue);
	}

	/**
	 * Creates a new placeholder with the given translation.
	 * @param key key
	 * @param translationKey translation
	 * @param type type
	 * @return value
	 */
	@Contract(value = "_, _, _, _ -> new", pure = true)
	static @NotNull Placeholder translation(@NotNull String key, @NotNull TranslationKey translationKey, @NotNull ComponentType type, @NotNull PlaceholderCollection collection){
		return new PlaceholderImpl(key, PlaceholderValue.translation(translationKey, type, collection));
	}
	/**
	 * Creates a new placeholder with the given translation.
	 * @param key key
	 * @param translationKey translation
	 * @param type type
	 * @return value
	 */
	@Contract(value = "_, _, _, _ -> new", pure = true)
	static @NotNull Placeholder translation(@NotNull String key, @NotNull TranslationKey translationKey, @NotNull ComponentType type, Placeholder... placeholders){
		return new PlaceholderImpl(key, PlaceholderValue.translation(translationKey, type, placeholders));
	}
	/**
	 * Creates a new placeholder with given date and parses it using given date format
	 * @param key key
	 * @param date date
	 * @param dateFormat format
	 * @return placeholder
	 */
	static @NotNull Placeholder date(@NotNull String key, @NotNull Date date, DateFormat dateFormat){
		return new PlaceholderImpl(key, PlaceholderValue.plain(dateFormat.format(date)));
	}
	/**
	 * Creates a new placeholder with given date and parses it using given date format
	 * @param key key
	 * @param date date
	 * @param dateFormat format
	 * @return placeholder
	 */
	static @NotNull Placeholder date(@NotNull String key, @NotNull Instant date, DateFormat dateFormat){
		return new PlaceholderImpl(key, PlaceholderValue.plain(dateFormat.format(Date.from(date))));
	}
	/**
	 * Creates a new placeholder with given date and parses it using given date format
	 * @param key key
	 * @param date date
	 * @param dateFormat format
	 * @return placeholder
	 */
	static @NotNull Placeholder date(@NotNull String key, long date, DateFormat dateFormat){
		return new PlaceholderImpl(key, PlaceholderValue.plain(dateFormat.format(new Date(date))));
	}
	/**
	 * Creates a new placeholder with given date and parses it using {@link DateFormat#getInstance()}
	 * @param key key
	 * @param date date
	 * @return placeholder
	 */
	static @NotNull Placeholder date(@NotNull String key, @NotNull Date date){
		return new PlaceholderImpl(key, PlaceholderValue.plain(DateFormat.getInstance().format(date)));
	}
	/**
	 * Creates a new placeholder with given date and parses it using {@link DateFormat#getInstance()}
	 * @param key key
	 * @param date date
	 * @return placeholder
	 */
	static @NotNull Placeholder date(@NotNull String key, @NotNull Instant date){
		return new PlaceholderImpl(key, PlaceholderValue.plain(DateFormat.getInstance().format(Date.from(date))));
	}
	/**
	 * Creates a new placeholder with given date and parses it using {@link DateFormat#getInstance()}
	 * @param key key
	 * @param date date
	 * @return placeholder
	 */
	static @NotNull Placeholder date(@NotNull String key, long date){
		return new PlaceholderImpl(key, PlaceholderValue.plain(DateFormat.getInstance().format(new Date(date))));
	}

	@NotNull
	@Override
	Placeholder clone(@NotNull String name);

	/**
	 * Returns the placeholder value associated with given placeholder.
	 * @return placeholder value
	 */
	@NotNull
	PlaceholderValue getPlaceholderValue();

	/**
	 * Returns the placeholder key
	 *
	 * @return key
	 */
	@NotNull
	String getKey();
}
