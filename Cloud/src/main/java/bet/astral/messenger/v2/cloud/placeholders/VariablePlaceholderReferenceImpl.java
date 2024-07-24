package bet.astral.messenger.v2.cloud.placeholders;

import bet.astral.messenger.v2.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

class VariablePlaceholderReferenceImpl implements VariablePlaceholderReference {
	private final Placeholder placeholder;

	public VariablePlaceholderReferenceImpl(Placeholder placeholder) {
		this.placeholder = placeholder;
	}

	@Override
	public @NotNull Placeholder clone(@NotNull String s) {
		return new VariablePlaceholderReferenceImpl(placeholder.clone(s));
	}

	@Override
	public @NotNull VariablePlaceholder toPlaceholder(@NotNull String key) {
		return new VariablePlaceholderReferenceImpl(placeholder.toPlaceholder(key));
	}

	@Override
	public @NotNull String getKey() {
		return placeholder.getKey();
	}

	@Override
	public @NotNull Component getValue() {
		return placeholder.getValue();
	}

	@Override
	public @NotNull Placeholder getReference() {
		return placeholder;
	}
}
