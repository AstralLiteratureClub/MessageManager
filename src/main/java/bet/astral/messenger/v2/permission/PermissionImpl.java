package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

record PermissionImpl<C extends Permissionable>(String permissionName) implements Permission<C> {
	PermissionImpl(@NotNull String permissionName) {
		this.permissionName = permissionName;
	}

	@Override
	public boolean test(@NotNull C receiver) {
		return receiver.hasPermission(permissionName);
	}
}
