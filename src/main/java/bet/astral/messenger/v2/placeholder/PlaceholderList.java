package bet.astral.messenger.v2.placeholder;

import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlaceholderList extends LinkedList<Placeholder> {
	public PlaceholderList() {
	}

	public PlaceholderList(@NotNull Collection<? extends Placeholder> c) {
		super(c);
	}

	public static Map<String, Placeholder> toMap(@NotNull Collection<Placeholder> placeholders){
		Map<String, Placeholder> placeholderMap = new HashMap<>();
		if (!placeholders.isEmpty()){
			placeholders.forEach(placeholder -> placeholderMap.put(placeholder.getKey(), placeholder));
		}
		return placeholderMap;
	}

	@Contract("_ -> new")
	public static @NotNull PlaceholderList toList(@NotNull Map<String, Placeholder> placeholders) {
		return new PlaceholderList(placeholders.values());
	}

	public Map<String, Placeholder> toMap(){
		return toMap(this);
	}

	public boolean add(String name, ComponentLike info) {
		return add(Placeholder.of(name, info));
	}
	public void add(int index, String name, ComponentLike info) {
		add(index, Placeholder.of(name, info));
	}
	public void add(String name,  PlaceholderValue placeholderValue){
		add(placeholderValue.toPlaceholder(name));
	}
	public void add(int index, String name,  PlaceholderValue placeholderValue){
		add(index, placeholderValue.toPlaceholder(name));
	}
	public void add(String[] names, ComponentLike info) {
		for (String name : names){
			add(Placeholder.of(name, info));
		}
	}
	public void add(int index, String[] names, ComponentLike info) {
		for (String name : names) {
			add(index, Placeholder.of(name, info));
		}
	}
	public void add(String[] names, PlaceholderValue placeholderValue) {
		for (String name : names) {
			add(placeholderValue.toPlaceholder(name));
		}
	}
	public void add(int index, String[] names,  PlaceholderValue placeholderValue){
		for (String name : names) {
			add(index, placeholderValue.toPlaceholder(name));
		}
	}


	public void addAll(Placeholder... placeholders){
		this.addAll(Arrays.asList(placeholders));
	}
	@Deprecated(forRemoval = true)
	public void addAll(String prefix, Placeholderable... placeholderables){
		this.addAll(prefix, Arrays.asList(placeholderables));
	}
	@Deprecated(forRemoval = true)
	public void addAll(String prefix, Collection<Placeholderable> placeholderables){
		for (Placeholderable placeholderable : placeholderables){
			this.addAll(placeholderable.toPlaceholders(prefix));
		}
	}

	public void add(String prefix, Placeholderable placeholderable){
		this.addAll(placeholderable.toPlaceholders(prefix));
	}
}
