package bet.astral.messenger.v3.cloud.placeholders;

import bet.astral.messenger.v2.placeholder.AbstractPlaceholder;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import bet.astral.messenger.v2.placeholder.values.RandomPlaceholderValue;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

class RandomVariablePlaceholderImpl extends AbstractPlaceholder implements VariablePlaceholder, RandomPlaceholderValue {
	private static final Random random = new Random(System.nanoTime() * 3232L * System.currentTimeMillis());
	private final List<ComponentLike> values = new LinkedList<>();

	public RandomVariablePlaceholderImpl(@NotNull String key, @NotNull Collection<Object> values) {
		super(key);
		for (Object object : values) {
			if (object instanceof PlaceholderValue value) {
				this.values.add(value);
			} else if (object instanceof String value) {
				this.values.add(Component.text(value));
			} else if (object instanceof Component value) {
				this.values.add(value);
			} else if (object instanceof ComponentLike value) {
				this.values.add(value);
			}
		}

	}

	protected RandomVariablePlaceholderImpl(@NotNull String key, @NotNull Placeholder placeholder) {
		super(key);
		if (placeholder instanceof RandomPlaceholderValue randomPlaceholderValue) {
			this.values.addAll(randomPlaceholderValue.getPossibleValues());
		} else {
			this.values.add(placeholder.getValue());
		}

	}

	protected RandomVariablePlaceholderImpl(@NotNull String key, @NotNull PlaceholderValue placeholder) {
		super(key);
		if (placeholder instanceof RandomPlaceholderValue randomPlaceholderValue) {
			this.values.addAll(randomPlaceholderValue.getPossibleValues());
		} else {
			this.values.add(placeholder.getValue());
		}

	}

	public @NotNull Component getValue() {
		if (this.values.isEmpty()) {
			return Component.empty();
		} else if (this.values.size() == 1) {
			return this.values.get(0).asComponent();
		} else {
			int valueId = this.getRandom().nextInt(this.values.size());
			ComponentLike value = this.values.get(valueId);
			return value.asComponent();
		}
	}

	public Random getRandom() {
		return random;
	}

	public @NotNull RandomVariablePlaceholderImpl clone(@NotNull String name) {
		return new RandomVariablePlaceholderImpl(name, this);
	}

	@Override
	public @NotNull PlaceholderValue getPlaceholderValue() {
		return this;
	}

	@SuppressWarnings({"MethodDoesntCallSuperMethod", "CloneDoesntDeclareCloneNotSupportedException"})
	protected Object clone() {
		return this.clone(this.getKey());
	}

	public @NotNull List<Component> getPossibleValues() {
		return this.values.stream().map(ComponentLike::asComponent).collect(Collectors.toList());
	}

	@Override
	public @NotNull Component asComponent() {
		return getValue();
	}

	@Override
	public @NotNull VariablePlaceholder toPlaceholder(@NotNull String key) {
		return clone(key);
	}
}
