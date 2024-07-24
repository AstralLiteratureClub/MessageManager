package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

class ComponentBaseImpl implements ComponentBase{
	private final TranslationKey translation;
	private final Map<ComponentType, ComponentPart> components;
	private final Map<String, Placeholder> placeholders;
	private boolean disabled;

	public ComponentBaseImpl(@NotNull TranslationKey translation, Map<ComponentType, ComponentPart> partMap, Map<String, Placeholder> placeholders) {
		this.translation = translation;
		this.components = partMap;
		this.placeholders = placeholders;
	}

	@Override
	public @NotNull TranslationKey getTranslationKey() {
		return translation;
	}

	@Override
	public @Nullable Map<@NotNull ComponentType, @NotNull ComponentPart> getParts() {
		return ImmutableMap.copyOf(components);
	}

	@Override
	public @Nullable Map<String, Placeholder> getPlaceholders() {
		return ImmutableMap.copyOf(placeholders);
	}

	@Override
	public boolean isDisabled() {
		return disabled;
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
