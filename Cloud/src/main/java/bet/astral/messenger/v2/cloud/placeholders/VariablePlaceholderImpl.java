package bet.astral.messenger.v2.cloud.placeholders;

import bet.astral.messenger.v2.placeholder.AbstractPlaceholder;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

class VariablePlaceholderImpl extends AbstractPlaceholder implements VariablePlaceholder{
	private final Component value;

	public VariablePlaceholderImpl(String key, Object value) {
		super(key);
		if (value instanceof PlaceholderValue placeholderValue){
			this.value = placeholderValue.getValue();
		} else if (value instanceof ComponentLike componentLike){
			this.value = componentLike.asComponent();
		} else if (value instanceof String s){
			this.value = Component.text(s);
		} else {
			this.value = Component.text(value.toString());
		}
	}

	@Override
	public @NotNull Placeholder clone(@NotNull String s) {
		return new VariablePlaceholderImpl(s, value);
	}

	@Override
	public @NotNull PlaceholderValue getPlaceholderValue() {
		return this;
	}

	@Override
	public @NotNull Component asComponent() {
		return VariablePlaceholder.super.asComponent();
	}

	@Override
	public @NotNull VariablePlaceholder toPlaceholder(@NotNull String key) {
		return VariablePlaceholder.super.toPlaceholder(key);
	}

	@Override
	public @NotNull Component getValue() {
		return value;
	}
}
