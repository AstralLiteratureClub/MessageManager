package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * Similar to {@link OrPermission} but allows multiple permissions to be given as parameters.
 * Returns true if any of the given permissions return true
 * @param <C> Permissionable
 */
public interface AnyPermission<C extends Permissionable> extends Permission<C> {
	/**
	 * Creates a new instance of {@link AnyPermission<C>}
	 * @param permissions permissions
	 * @return new linked permission
	 * @param <C> Permissionable
	 */
	@NotNull
	static <C extends Permissionable> AnyPermission<C> of(@NotNull Permission<C>... permissions){
		return new AnyPermissionImpl<>(Arrays.stream(permissions).toList());
	}

	/**
	 * Returns all the permissions linked to this permission.
	 * @return permissions
	 */
	@NotNull
	Collection<Permission<C>> permissions();
}
