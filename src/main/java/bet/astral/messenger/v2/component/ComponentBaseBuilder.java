package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComponentBaseBuilder {
	private final @NotNull TranslationKey translation;
	private final Map<String, Placeholder> placeholders = new HashMap<>();
	private final Map<ComponentType, ComponentPart> partMap = new HashMap<>();

	public ComponentBaseBuilder(@NotNull TranslationKey translation) {
		this.translation = translation;
	}

	public void addPlaceholders(@NotNull Map<String, ? extends Placeholder> placeholders) {
		this.placeholders.putAll(placeholders);
	}
	public void addPlaceholder(@NotNull Placeholder placeholder){
		this.placeholders.put(placeholder.getKey(), placeholder);
	}
	public void addPlaceholders(@NotNull Placeholder... placeholders){
		this.placeholders.putAll(Stream.of(placeholders).collect(Collectors.toMap(Placeholder::getKey, placeholder -> placeholder)));
	}
	public void addPlaceholders(@NotNull Collection<? extends Placeholder> placeholders){
		this.placeholders.putAll(placeholders.stream().collect(Collectors.toMap(Placeholder::getKey, placeholder -> placeholder)));
	}

	public void setComponentPart(@NotNull ComponentType componentType, @NotNull ComponentPart componentPart){
		this.partMap.put(componentType, componentPart);
	}

	public ComponentBase build() {
		return new ComponentBaseImpl(translation, partMap, placeholders);
	}
}