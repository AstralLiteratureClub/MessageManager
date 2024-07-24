package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

final class AllPermissionImpl implements AllPermission {
	private final List<Permission> permissions;

	AllPermissionImpl(List<Permission> permissions) {
		this.permissions = permissions;
	}
	public AllPermissionImpl(Permission[] permissions) {
		this.permissions = List.of(permissions);
	}

	@Override
	public boolean test(@NotNull Permissionable receiver) {
		for (Permission permission : permissions) {
			if (!permission.test(receiver)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public @NotNull List<Permission> permissions() {
		return permissions;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (AllPermissionImpl) obj;
		return Objects.equals(this.permissions, that.permissions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(permissions);
	}
}
