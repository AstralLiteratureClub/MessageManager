package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

/**
 * Requires both permissions to be true to return true
 * @param <C> permissionable
 */
public interface AndPermission<C extends Permissionable> extends Permission<C>{
	/**
	 * Creates a new instance of {@link AndPermission<C>}
	 * @param one first permission
	 * @param two second permission
	 * @return combined permission
	 * @param <C> Permissionable
	 */
	@NotNull
	static <C extends Permissionable> AndPermission<C> of(@NotNull Permission<C> one, @NotNull Permission<C> two){
		return new AndPermissionImpl<>(one, two);
	}

	/**
	 * The first permission
	 * @return permission
	 */
	@NotNull
	Permission<C> getFirst();

	/**
	 * The second permission
	 * @return permission
	 */
	@NotNull
	Permission<C> getSecond();
}
