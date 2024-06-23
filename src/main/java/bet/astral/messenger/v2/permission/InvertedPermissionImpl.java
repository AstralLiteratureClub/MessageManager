package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class InvertedPermissionImpl implements InvertedPermission {
	private final @NotNull Permission permission;

	InvertedPermissionImpl(@NotNull Permission permission) {
		this.permission = permission;
	}

	@Override
	public boolean test(@NotNull Permissionable receiver) {
		return !permission.test(receiver);
	}

	@Override
	public @NotNull Permission permission() {
		return permission;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (InvertedPermissionImpl) obj;
		return Objects.equals(this.permission, that.permission);
	}

	@Override
	public int hashCode() {
		return Objects.hash(permission);
	}
}
