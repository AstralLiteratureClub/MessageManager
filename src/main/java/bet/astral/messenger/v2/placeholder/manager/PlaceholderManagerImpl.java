package bet.astral.messenger.v2.placeholder.manager;

import bet.astral.messenger.v2.placeholder.Placeholder;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class PlaceholderManagerImpl implements PlaceholderManager{
	private final Map<String, Placeholder> placeholderMap = new HashMap<>();
	@Override
	public @NotNull Map<String, Placeholder> getGlobalPlaceholders() {
		return ImmutableMap.copyOf(placeholderMap);
	}

	@Override
	public PlaceholderManager overridePlaceholder(@NotNull Placeholder placeholder) {
		this.placeholderMap.put(placeholder.getKey().toLowerCase(), placeholder);
		return this;
	}

	@Override
	public PlaceholderManager overridePlaceholders(@NotNull Placeholder... placeholders) {
		for (Placeholder placeholder : placeholders){
			overridePlaceholder(placeholder);
		}
		return this;
	}

	@Override
	public PlaceholderManager overridePlaceholders(@NotNull Collection<? extends @NotNull Placeholder> placeholders) {
		for (Placeholder placeholder : placeholders){
			overridePlaceholder(placeholder);
		}
		return this;
	}

	@Override
	public PlaceholderManager overrideAllPlaceholders(@NotNull Map<@NotNull String, @NotNull Placeholder> placeholders) {
		for (Map.Entry<String, Placeholder> entry : placeholders.entrySet()){
			overridePlaceholder(entry.getValue());
		}
		return this;
	}
}
