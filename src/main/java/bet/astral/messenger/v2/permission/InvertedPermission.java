package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

/**
 * Inverts any permission given as its permission.
 * @param <C> Permissionable
 */
public interface InvertedPermission<C extends Permissionable> extends Permission<C>{
	/**
	 * Returns new permission inverter
	 * @param permission permission to invert
	 * @return new inverted permission
	 */
	@NotNull
	static <C extends Permissionable> InvertedPermission<C> of(@NotNull Permission<C> permission){
		return new InvertedPermissionImpl<>(permission);
	}

	/**
	 * Returns the permission which is inverted
	 * @return permission
	 */
	@NotNull
	Permission<C> permission();
}
