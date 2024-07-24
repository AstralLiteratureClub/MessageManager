package bet.astral.messenger.v2.placeholder;

import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

class PlaceholderImpl extends AbstractPlaceholder{
	private final Component value;

	protected PlaceholderImpl(@NotNull String key, @NotNull Component value) {
		super(key);
		this.value = value;
	}
	protected PlaceholderImpl(@NotNull String key, @NotNull PlaceholderValue placeholderValue){
		super(key);
		this.value = placeholderValue.getValue();
	}


	@Override
	public @NotNull Placeholder clone(@NotNull String name) {
		return new PlaceholderImpl(name, value);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return clone(getKey());
	}

	@Override
	public @NotNull Component getValue() {
		return value;
	}
}
