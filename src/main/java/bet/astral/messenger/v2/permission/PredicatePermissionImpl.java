package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public record PredicatePermissionImpl<C extends Permissionable>(Predicate<C> predicate) implements PredicatePermission<C> {
	public PredicatePermissionImpl(@NotNull Predicate<C> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean test(@NotNull C receiver) {
		return getPredicate().test(receiver);
	}

	@Override
	public @NotNull Predicate<C> predicate() {
		return predicate;
	}

	@Override
	public @NotNull Predicate<C> getPredicate() {
		return predicate();
	}
}
