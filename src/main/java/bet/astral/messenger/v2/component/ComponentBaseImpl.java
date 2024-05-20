package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

class ComponentBaseImpl implements ComponentBase{
	private final TranslationKey translation;
	private final Map<Locale, Map<ComponentType, ComponentPart>> components = new HashMap<>();
	private final Map<Locale, Map<String, Placeholder>> placeholders = new HashMap<>();
	private final Set<Locale> disabledLanguages = new HashSet<>();

	public ComponentBaseImpl(@NotNull TranslationKey translation) {
		this.translation = translation;
	}

	@Override
	public @NotNull TranslationKey getTranslationKey() {
		return translation;
	}

	@Override
	public @Nullable Map<@NotNull ComponentType, @NotNull ComponentPart> getParts(Locale locale) {
		Map<ComponentType, ComponentPart> parts = components.get(locale);
		if (parts == null){
			return null;
		}
		return ImmutableMap.copyOf(parts);
	}

	@Override
	public @NotNull Collection<@NotNull Locale> getLocales() {
		return ImmutableList.copyOf(components.keySet());
	}

	@Override
	public @Nullable Map<String, Placeholder> getPlaceholders(@NotNull Locale locale) {
		Map<String, Placeholder> placeholderMap = placeholders.get(locale);
		if (placeholderMap == null){
			return null;
		}
		return ImmutableMap.copyOf(placeholderMap);
	}

	@Override
	public boolean isDisabled(@NotNull Locale locale) {
		return disabledLanguages.contains(locale);
	}

	@Override
	public void setDisabled(@NotNull Locale locale, boolean disabled) {
		if (disabled){
			disabledLanguages.add(locale);
			return;
		}
		disabledLanguages.remove(locale);
	}

	@Override
	public void addLocale(@NotNull Locale locale, @NotNull Map<@NotNull ComponentType, @NotNull ComponentPart> parts) {
		this.components.put(locale, parts);
	}

	@Override
	public void removeLocale(@NotNull Locale locale) {
		this.components.remove(locale);
	}
}
