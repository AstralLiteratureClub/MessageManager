package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @param <C>
 */
final class AllPermissionImpl<C extends Permissionable> implements AllPermission<C> {
	private final List<Permission<C>> permissions;

	AllPermissionImpl(List<Permission<C>> permissions) {
		this.permissions = permissions;
	}
	public AllPermissionImpl(Permission<C>[] permissions) {
		this.permissions = List.of(permissions);
	}

	@Override
	public boolean test(@NotNull C receiver) {
		for (Permission<C> permission : permissions) {
			if (!permission.test(receiver)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public @NotNull List<Permission<C>> permissions() {
		return permissions;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (AllPermissionImpl<?>) obj;
		return Objects.equals(this.permissions, that.permissions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(permissions);
	}

	@Override
	public String toString() {
		return "AllPermissionImpl[" +
				"permissions=" + permissions + ']';
	}

}
