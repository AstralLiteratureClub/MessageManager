package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface Permission<C extends Permissionable> {
	Permission<Permissionable> empty = new EmptyPermissionImpl<>();
	static <C extends Permissionable> Permission<C> empty(){
		return new EmptyPermissionImpl<>();
	}
	@NotNull
	static <C extends Permissionable> Permission<C> of(@NotNull String permission){
		return new PermissionImpl<>(permission);
	}
	boolean test(@NotNull C permissionable);

	@NotNull
	default Permission<C> or(@NotNull Permission<C> permission){
		return new OrPermissionImpl<>(this, permission);
	}
	@NotNull
	default Permission<C> or(@NotNull Predicate<C> predicate){
		return new OrPermissionImpl<>(this, PredicatePermission.of(predicate));
	}
	@NotNull
	default Permission<C> and(@NotNull Permission<C> permission) {
		return new AndPermissionImpl<>(this, permission);
	}
	@NotNull
	default Permission<C> and(@NotNull Predicate<C> predicate) {
		return new AndPermissionImpl<>(this, PredicatePermission.of(predicate));
	}

	@NotNull
	default Permission<C> inverted(){
		return InvertedPermission.of(this);
	}
}
