package bet.astral.messenger.v2.cloud.permissions;

import bet.astral.messenger.v2.permission.Permissionable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.permission.PermissionResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;


class CloudPermissionImpl<C extends Permissionable> implements CloudPermission<C>{
	private final Predicate<Permissionable> predicate;

	public CloudPermissionImpl(Predicate<Permissionable> predicate) {
		this.predicate = predicate;
	}

	@Override
	public @NotNull Predicate<Permissionable> getPredicate() {
		return predicate;
	}

	@Override
	public boolean test(@NotNull Permissionable permissionable) {
		return predicate.test(permissionable);
	}

	@Override
	public @NonNull PermissionResult testPermission(@NonNull C sender) {
		return PermissionResult.of(predicate.test(sender), this);
	}
}
