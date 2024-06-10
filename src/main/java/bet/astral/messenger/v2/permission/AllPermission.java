package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Requires all permissions in the collection to be true
 * @param <C> permissionable
 */
public interface AllPermission<C extends Permissionable> extends Permission<C>{
	/**
	 * Creates a new instance of {@link AllPermission<C>}
	 * @param permissions permissions to link
	 * @return new linked permission
	 * @param <C> Permissionable
	 */
	@SafeVarargs
	@NotNull
	static <C extends Permissionable> Permission<C> of(@NotNull Permission<C>... permissions){
		return new AllPermissionImpl<>(permissions);
	}

	/**
	 * Returns all the permissions in this one permission
	 * @return permissions
	 */
	@NotNull
	Collection<@NotNull Permission<C>> permissions();
}
