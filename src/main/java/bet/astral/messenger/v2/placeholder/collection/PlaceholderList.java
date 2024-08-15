package bet.astral.messenger.v2.placeholder.collection;

import bet.astral.messenger.v2.placeholder.Placeholder;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlaceholderList extends LinkedList<Placeholder> implements PlaceholderCollection{
	public PlaceholderList() {
	}

	public PlaceholderList(@NotNull Collection<? extends Placeholder> c) {
		super(c);
	}

	@Override
	public @NotNull PlaceholderMap toMap() {
		return PlaceholderCollection.map(this);
	}

	@Override
	public @NotNull PlaceholderList toList() {
		return this;
	}

	@Override
	public boolean addAll(@NotNull Collection<? extends Placeholder> c) {
		return super.addAll(c);
	}

	@Override
	public boolean addCollection(@NotNull Collection<? extends Placeholder> placeholders) {
		return addAll(placeholders);
	}

	@Override
	public boolean addMap(@NotNull Map<? extends String, ? extends Placeholder> placeholders) {
		return addAll(placeholders.values());
	}

	@Override
	@NotNull
	public Placeholder @NotNull[] toArray() {
		return toArray(Placeholder[]::new);
	}

	@Override
	public <T> T @NotNull [] toArray(T[] a) {
		return super.toArray(a);
	}

	@Override
	public boolean add(@NotNull Placeholder placeholder) {
		return super.add(placeholder);
	}
}
