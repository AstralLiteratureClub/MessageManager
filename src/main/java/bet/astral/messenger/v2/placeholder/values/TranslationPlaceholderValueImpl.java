package bet.astral.messenger.v2.placeholder.values;

import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.translation.TranslationKey;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

class TranslationPlaceholderValueImpl implements TranslationPlaceholderValue{
	private final TranslationKey key;
	private final ComponentType componentType;

	@Contract(pure = true)
	public TranslationPlaceholderValueImpl(TranslationKey key, ComponentType componentType) {
		this.key = key;
		this.componentType = componentType;
	}

	@Override
	public @NotNull ComponentType getValueComponentType() {
		return componentType;
	}

	@Override
	public @NotNull TranslationKey getTranslationKey() {
		return key;
	}

	@Override
	public @NotNull Component getValue() {
		return Component.translatable(key);
	}
}
