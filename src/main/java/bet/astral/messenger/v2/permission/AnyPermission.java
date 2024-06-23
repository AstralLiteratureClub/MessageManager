package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * Similar to {@link OrPermission} but allows multiple permissions to be given as parameters.
 * Returns true if any of the given permissions return true
 */
public interface AnyPermission extends Permission {
	/**
	 * Creates a new instance of {@link AnyPermission}
	 * @param permissions permissions
	 * @return new linked permission
	 */
	@NotNull
	static AnyPermission of(@NotNull Permission... permissions){
		return new AnyPermissionImpl(Arrays.stream(permissions).toList());
	}

	/**
	 * Returns all the permissions linked to this permission.
	 * @return permissions
	 */
	@NotNull
	Collection<Permission> permissions();
}
