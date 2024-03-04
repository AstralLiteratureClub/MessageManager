package bet.astral.messenger.placeholder;

import bet.astral.messenger.utils.PlaceholderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

public class PlaceholderList extends LinkedList<Placeholder> {
	public PlaceholderList() {
	}

	public PlaceholderList(@NotNull Collection<? extends Placeholder> c) {
		super(c);
	}

	public boolean add(String name, Object info) {
		return add(PlaceholderUtils.createPlaceholder(null, name, info));
	}
	public void add(int index, String name, Object info) {
		add(index, PlaceholderUtils.createPlaceholder(null, name, index));
	}
}
