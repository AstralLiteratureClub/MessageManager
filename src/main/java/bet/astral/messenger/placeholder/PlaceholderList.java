package bet.astral.messenger.placeholder;

import bet.astral.messenger.utils.PlaceholderUtils;

import java.util.LinkedList;

public class PlaceholderList extends LinkedList<Placeholder> {
	public boolean add(String name, Object info) {
		return add(PlaceholderUtils.createPlaceholder(null, name, info));
	}
	public void add(int index, String name, Object info) {
		add(index, PlaceholderUtils.createPlaceholder(null, name, index));
	}
}
