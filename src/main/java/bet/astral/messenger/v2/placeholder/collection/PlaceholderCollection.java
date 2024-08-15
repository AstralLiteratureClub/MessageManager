package bet.astral.messenger.v2.placeholder.collection;

import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public interface PlaceholderCollection extends Iterable<Placeholder> {
	static @NotNull PlaceholderList list(@NotNull Placeholder... placeholders){
		PlaceholderList placeholderList = new PlaceholderList();
		placeholderList.addAll(placeholders);
		return placeholderList;
	}
	static @NotNull PlaceholderList list(@NotNull PlaceholderList @NotNull ... placeholderLists){
		PlaceholderList placeholderList = new PlaceholderList();
		for (PlaceholderList placeholders : placeholderLists){
			placeholderList.addAll(placeholders);
		}
		return placeholderList;
	}
	static @NotNull PlaceholderList list(@NotNull PlaceholderMap @NotNull ... placeholderMaps){
		PlaceholderList placeholderMap = new PlaceholderList();
		for (PlaceholderMap placeholders : placeholderMaps){
			placeholderMap.addAll(placeholders);
		}
		return placeholderMap;
	}
	static @NotNull PlaceholderList list(@NotNull Collection<Placeholder> @NotNull ... placeholderCollection){
		PlaceholderList placeholderMap = new PlaceholderList();
		for (Collection<Placeholder> placeholders : placeholderCollection){
			placeholderMap.addAll(placeholders);
		}
		return placeholderMap;
	}
	static @NotNull PlaceholderList list(@NotNull Map<String, Placeholder> @NotNull ... placeholderCollection){
		PlaceholderList placeholderMap = new PlaceholderList();
		for (Map<String, Placeholder> placeholders : placeholderCollection){
			placeholderMap.addAll(placeholders.values());
		}
		return placeholderMap;
	}
	static @NotNull PlaceholderMap map(@NotNull Placeholder... placeholders){
		PlaceholderMap placeholderList = new PlaceholderMap();
		placeholderList.addAll(placeholders);
		return placeholderList;
	}
	static @NotNull PlaceholderMap map(@NotNull PlaceholderList @NotNull ... placeholderLists){
		PlaceholderMap placeholderMap = new PlaceholderMap();
		for (PlaceholderList placeholders : placeholderLists){
			placeholderMap.addAll(placeholders);
		}
		return placeholderMap;
	}
	static @NotNull PlaceholderMap map(@NotNull PlaceholderMap @NotNull ... placeholderMaps){
		PlaceholderMap placeholderMap = new PlaceholderMap();
		for (PlaceholderMap placeholders : placeholderMaps){
			placeholderMap.addAll(placeholders);
		}
		return placeholderMap;
	}
	static @NotNull PlaceholderMap map(@NotNull Collection<Placeholder> @NotNull ... placeholderCollection) {
		PlaceholderMap placeholderMap = new PlaceholderMap();
		for (Collection<Placeholder> placeholders : placeholderCollection){
			placeholderMap.addAll(placeholders);
		}
		return placeholderMap;
	}
	static @NotNull PlaceholderMap map(@NotNull Map<String, Placeholder> @NotNull ... placeholderLists){
		PlaceholderMap placeholderMap = new PlaceholderMap();
		for (Map<String, Placeholder> placeholders : placeholderLists){
			placeholderMap.addMap(placeholders);
		}
		return placeholderMap;
	}
	@NotNull
	PlaceholderMap toMap();
	@NotNull
	PlaceholderList toList();

	boolean contains(Object o);

	@NotNull Placeholder @NotNull [] toArray();

	@NotNull <T> T @NotNull [] toArray(@NotNull T[] a);

	boolean add(@NotNull Placeholder placeholder);

	default void add(@NotNull String prefix, @NotNull PlaceholderValue placeholderValue) {
		add(Placeholder.of(prefix, placeholderValue));
	}
	default void add(@NotNull String name, @NotNull String s) {
		add(name, Component.text(s));
	}
	default void add(@NotNull String name, @NotNull Number number) {
		add(name, Component.text(number.toString()));
	}

	default void add(@NotNull String name, @NotNull ComponentLike componentLike) {
		add(name, PlaceholderValue.of(componentLike));
	}

	default void add(@NotNull String[] names, @NotNull String s) {
		add(names, Component.text(s));
	}
	default void add(@NotNull String[] names, @NotNull Number number) {
		add(names, Component.text(number.toString()));
	}
	default void add(@NotNull String[] names, @NotNull ComponentLike componentLike) {
		add(names, PlaceholderValue.of(componentLike));
	}
	default void add(@NotNull String[] names, @NotNull PlaceholderValue placeholderValue) {
		for (String name : names){
			add(name, placeholderValue);
		}
	}

	default void addAll(@NotNull Placeholder... placeholders) {
		for (Placeholder placeholder : placeholders){
			add(placeholder);
		}
	}

	boolean containsAll(@NotNull Collection<?> c);

	boolean addAll(@NotNull Collection<? extends Placeholder> c);

	boolean removeAll(@NotNull Collection<?> c);

	boolean retainAll(@NotNull Collection<?> c);

	boolean addCollection(@NotNull Collection<? extends Placeholder> placeholders);
	boolean addMap(@NotNull Map<? extends String, ? extends Placeholder> placeholders);

	default void addAll(@NotNull PlaceholderCollection placeholders) {
		for (Placeholder placeholder : placeholders){
			this.add(placeholder);
		}
	}
	default void addAll(@NotNull PlaceholderList placeholders) {
		for (Placeholder placeholder : placeholders){
			this.add(placeholder);
		}
	}
	default void addAll(@NotNull PlaceholderMap placeholders) {
		for (Placeholder placeholder : placeholders){
			this.add(placeholder);
		}
	}
	@NotNull
	default Stream<Placeholder> streamPlaceholders(){
		PlaceholderList placeholders = toList();
		return Stream.of(placeholders.toArray(Placeholder[]::new));
	}
}
