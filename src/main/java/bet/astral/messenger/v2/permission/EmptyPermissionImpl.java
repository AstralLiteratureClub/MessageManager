package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

/**
 * An empty permission.
 * @param <C>
 */
final class EmptyPermissionImpl<C extends Permissionable> implements Permission<C> {
	/**
	 * Returns true
	 * @param permissionable permissionable
	 * @return true
	 */
	@Override
	public boolean test(@NotNull C permissionable) {
		return true;
	}
}
