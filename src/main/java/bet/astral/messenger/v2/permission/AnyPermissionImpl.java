package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

final class AnyPermissionImpl<C extends Permissionable> implements AnyPermission<C> {
	private final @NotNull Collection<@NotNull Permission<C>> permissions;

	AnyPermissionImpl(@NotNull Collection<@NotNull Permission<C>> permissions) {
		this.permissions = permissions;
	}

	@Override
	public boolean test(@NotNull C permissionable) {
		for (Permission<C> permission : permissions) {
			if (permission.test(permissionable)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public @NotNull Collection<@NotNull Permission<C>> permissions() {
		return permissions;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (AnyPermissionImpl<?>) obj;
		return Objects.equals(this.permissions, that.permissions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(permissions);
	}

	@Override
	public String toString() {
		return "AnyPermissionImpl[" +
				"permissions=" + permissions + ']';
	}

}
