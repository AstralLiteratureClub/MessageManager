package bet.astral.messenger.v2.placeholder;

import bet.astral.messenger.v2.utils.Randomly;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import bet.astral.messenger.v2.placeholder.values.RandomPlaceholderValue;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class RandomPlaceholderImpl extends AbstractPlaceholder implements Randomly, RandomPlaceholderValue {
	private static final Random random = new Random(System.nanoTime()*1321*System.currentTimeMillis());
	private final List<ComponentLike> values = new LinkedList<>();

	public RandomPlaceholderImpl(@NotNull String key, @NotNull Collection<ComponentLike> values) {
		super(key);
		this.values.addAll(values);
	}
	protected RandomPlaceholderImpl(@NotNull String key, @NotNull Placeholder placeholder){
		super(key);
		if (placeholder instanceof RandomPlaceholderValue randomPlaceholderValue){
			this.values.addAll(randomPlaceholderValue.getPossibleValues());
		} else {
			this.values.add(placeholder.getValue());
		}
	}
	protected RandomPlaceholderImpl(@NotNull String key, @NotNull PlaceholderValue placeholder){
		super(key);
		if (placeholder instanceof RandomPlaceholderValue randomPlaceholderValue) {
			this.values.addAll(randomPlaceholderValue.getPossibleValues());
		} else {
			this.values.add(placeholder.getValue());
		}
	}

	@Override
	public @NotNull Component getValue() {
		if (values.isEmpty()){
			return Component.empty();
		} else if (values.size()==1){
			return values.get(0).asComponent();
		}
		int valueId = getRandom().nextInt(values.size());
		ComponentLike value = values.get(valueId);
		return value.asComponent();
	}

	@Override
	public Random getRandom() {
		return random;
	}

	@Override
	public @NotNull RandomPlaceholderImpl clone(@NotNull String name) {
		return new RandomPlaceholderImpl(name, this);
	}

	@Override
	public @NotNull PlaceholderValue getPlaceholderValue() {
		return this;
	}

	@SuppressWarnings("MethodDoesntCallSuperMethod")
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return clone(getKey());
	}

	@Override
	public @NotNull List<Component> getPossibleValues() {
		return values.stream().map(ComponentLike::asComponent).collect(Collectors.toList());
	}
}
