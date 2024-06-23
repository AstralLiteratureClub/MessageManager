package bet.astral.messenger.v2.permission;

import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

record PermissionImpl(String permissionName) implements Permission {
	PermissionImpl(@NotNull String permissionName) {
		this.permissionName = permissionName;
	}

	@Override
	public boolean test(@NotNull Permissionable receiver) {
		return receiver.hasPermission(permissionName);
	}
}
