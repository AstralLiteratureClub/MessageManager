package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;


public interface Permission {
	Permission empty = new EmptyPermissionImpl();
	static  Permission empty(){
		return new EmptyPermissionImpl();
	}
	@NotNull
	static  Permission of(@NotNull String permission){
		return new PermissionImpl(permission);
	}
	boolean test(@NotNull Permissionable permissionable);

	@NotNull
	default Permission or(@NotNull Permission permission){
		return new OrPermissionImpl(this, permission);
	}
	@NotNull
	default Permission or(@NotNull Predicate<Permissionable> predicate){
		return new OrPermissionImpl(this, PredicatePermission.of(predicate));
	}
	@NotNull
	default Permission and(@NotNull Permission permission) {
		return new AndPermissionImpl(this, permission);
	}
	@NotNull
	default Permission and(@NotNull Predicate<Permissionable> predicate) {
		return new AndPermissionImpl(this, PredicatePermission.of(predicate));
	}

	@NotNull
	default Permission inverted(){
		return InvertedPermission.of(this);
	}
}
