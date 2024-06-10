package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

/**
 * Or permission as the name returns true if any of the given 2 permissions return true.
 * @param <C> Permissionable
 */
public interface OrPermission<C extends Permissionable> extends Permission<C>{
	/**
	 * Creates a new instance of {@link OrPermission<C>}
	 * @param one first permission
	 * @param two second perission
	 * @return new or permission
	 * @param <C> Permissionable
	 */
	@NotNull
	static <C extends Permissionable> OrPermission<C> of(@NotNull Permission<C> one, @NotNull Permission<C> two){
		return new OrPermissionImpl<>(one, two);
	}
	@NotNull
	Permission<C> getFirst();
	@NotNull
	Permission<C> getSecond();
}
