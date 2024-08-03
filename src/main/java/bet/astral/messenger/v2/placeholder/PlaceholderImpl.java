package bet.astral.messenger.v2.placeholder;

import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

class PlaceholderImpl extends AbstractPlaceholder{
	private final PlaceholderValue value;

	protected PlaceholderImpl(@NotNull String key, @NotNull Component value) {
		super(key);
		this.value = PlaceholderValue.of(value);
	}
	protected PlaceholderImpl(@NotNull String key, @NotNull PlaceholderValue placeholderValue){
		super(key);
		this.value = placeholderValue;
	}


	@Override
	public @NotNull Placeholder clone(@NotNull String name) {
		return new PlaceholderImpl(name, value);
	}

	@Override
	public @NotNull PlaceholderValue getPlaceholderValue() {
		return value;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return clone(getKey());
	}

	@Override
	public @NotNull Component getValue() {
		return value.getValue();
	}
}
