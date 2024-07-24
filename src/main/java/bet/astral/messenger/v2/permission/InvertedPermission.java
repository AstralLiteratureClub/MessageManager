package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

/**
 * Inverts any permission given as its permission.
 */
public interface InvertedPermission extends Permission{
	/**
	 * Returns new permission inverter
	 * @param permission permission to invert
	 * @return new inverted permission
	 */
	@NotNull
	static InvertedPermission of(@NotNull Permission permission){
		return new InvertedPermissionImpl(permission);
	}

	/**
	 * Returns the permission which is inverted
	 * @return permission
	 */
	@NotNull
	Permission permission();
}
