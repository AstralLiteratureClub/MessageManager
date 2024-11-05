package bet.astral.messenger.v3.cloud.permissions;

import bet.astral.messenger.v2.permission.Permissionable;
import bet.astral.messenger.v2.permission.PredicatePermission;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface CloudPermission<C extends Permissionable> extends PredicatePermission, org.incendo.cloud.permission.PredicatePermission<C> {
	@NotNull
	static <C extends Permissionable> CloudPermission<C> of(Predicate<C> predicate){
		//noinspection unchecked
		return new CloudPermissionImpl<>((Predicate<Permissionable>) predicate);
	}

	static <C extends Permissionable> CloudPermission<C> of(@NotNull String permission){
		return new CloudPermissionImpl<>(c->c.hasPermission(permission));
	}
}
