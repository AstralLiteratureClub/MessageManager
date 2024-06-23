package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

/**
 * An empty permission.
 */
final class EmptyPermissionImpl implements Permission {
	/**
	 * Returns true
	 * @param permissionable permissionable
	 * @return true
	 */
	@Override
	public boolean test(@NotNull Permissionable permissionable) {
		return true;
	}
}
