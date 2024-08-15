package bet.astral.messenger.v2.placeholder.collection;

import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlaceholderMap extends HashMap<String, Placeholder> implements PlaceholderCollection {
	@SuppressWarnings("unused")
	public PlaceholderMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	@SuppressWarnings("unused")
	public PlaceholderMap(int initialCapacity) {
		super(initialCapacity);
	}

	@SuppressWarnings("unused")
	public PlaceholderMap() {
	}

	@SuppressWarnings("unused")
	public PlaceholderMap(Map<? extends String, ? extends Placeholder> m) {
		super(m);
	}

	@Override
	public @NotNull PlaceholderMap toMap() {
		return this;
	}

	@Override
	public @NotNull PlaceholderList toList() {
		return PlaceholderCollection.list(this);
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof String key) {
			return containsValue(key.toLowerCase());
		} else if (o instanceof Placeholder placeholder){
			return containsValue(placeholder);
		}
		return false;
	}

	@Override
	public @NotNull Placeholder @NotNull [] toArray() {
		return values().toArray(Placeholder[]::new);
	}

	@Override
	public @NotNull <T> T @NotNull [] toArray(@NotNull T[] a) {
		return values().toArray(a);
	}

	@Override
	public boolean add(@NotNull Placeholder placeholder) {
		put(placeholder.getKey(), placeholder);
		return true;
	}

	@Override
	public void add(@NotNull String prefix, @NotNull PlaceholderValue placeholderValue) {
		PlaceholderCollection.super.add(prefix, placeholderValue);
	}

	@Override
	public boolean containsAll(@NotNull Collection<?> c) {
		boolean contains = true;
		for (Object o : c){
			contains = contains(o);
			if (!contains){
				break;
			}
		}
		return contains;
	}

	@Override
	public boolean addAll(@NotNull Collection<? extends Placeholder> c) {
		boolean changed = false;
		for (Placeholder placeholder : c){
			if (!contains(placeholder.getKey())){
				changed = true;
			}
			add(placeholder);
		}
		return changed;
	}

	@Override
	public boolean removeAll(@NotNull Collection<?> c) {
		boolean changed = false;
		for (Object placeholder : c){
			if (placeholder instanceof String key){
				Placeholder value = remove(key);
				if (value != null){
					changed = true;
				}
			} else if (placeholder instanceof Placeholder placeholderReal){
				Placeholder value = remove(placeholderReal.getKey());
				if (value != null){
					changed = true;
				}
			}
		}
		return changed;
	}

	@Override
	public boolean retainAll(@NotNull Collection<?> c) {
		PlaceholderMap map = new PlaceholderMap();
		for (Object object : c){
			if (object instanceof String key){
				Placeholder placeholder = get(key);
				if (placeholder == null){
					continue;
				}
				map.put(placeholder.getKey(), placeholder);
			} else if (object instanceof Placeholder placeholder){
				if (contains(placeholder)){
					map.put(placeholder.getKey(), placeholder);
				}
			}
		}
		boolean changed = map.size() != size();
		if (!changed){
			return false;
		}
		clear();
		addMap(map);
		return true;
	}

	@Override
	public boolean addCollection(@NotNull Collection<? extends Placeholder> placeholders) {
		for (Placeholder placeholder : placeholders){
			put(placeholder.getKey(), placeholder);
		}
		return true;
	}

	@Override
	public boolean addMap(@NotNull Map<? extends String, ? extends Placeholder> placeholders) {
		for (Placeholder placeholder : placeholders.values()){
			put(placeholder.getKey(), placeholder);
		}
		return true;
	}

	@Override
	public @NotNull Iterator<Placeholder> iterator() {
		return values().iterator();
	}

	@Override
	public Placeholder remove(Object key) {
		return super.remove(key);
	}
}
