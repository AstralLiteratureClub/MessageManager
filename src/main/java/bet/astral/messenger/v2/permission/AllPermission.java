package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Requires all permissions in the collection to be true
 */
public interface AllPermission extends Permission{
	/**
	 * Creates a new instance of {@link AllPermission}
	 * @param permissions permissions to link
	 * @return new linked permission
	 */
	@NotNull
	static Permission of(Permission... permissions){
		return new AllPermissionImpl(permissions);
	}

	/**
	 * Returns all the permissions in this one permission
	 * @return permissions
	 */
	@NotNull
	Collection<@NotNull Permission> permissions();
}
