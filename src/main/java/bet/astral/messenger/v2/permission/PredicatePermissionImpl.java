package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public record PredicatePermissionImpl(Predicate<Permissionable> predicate) implements PredicatePermission {
	public PredicatePermissionImpl(@NotNull Predicate<Permissionable> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean test(@NotNull Permissionable receiver) {
		return getPredicate().test(receiver);
	}

	@Override
	public @NotNull Predicate<Permissionable> predicate() {
		return predicate;
	}

	@Override
	public @NotNull Predicate<Permissionable> getPredicate() {
		return predicate();
	}
}
