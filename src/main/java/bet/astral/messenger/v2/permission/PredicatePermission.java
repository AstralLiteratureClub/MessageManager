package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Predicate permission checks the given predicate, and if the predicate returns true then the permission returns true
 * @param <C> Permissionable
 */
public interface PredicatePermission<C extends Permissionable> extends Permission<C>{
	/**
	 * Creates a new instance of predicate permission
	 * @param predicate predicate
	 * @return predicate permission
	 * @param <C> Permissionable
	 */
	@NotNull
	static <C extends Permissionable> Permission<C> of(@NotNull Predicate<C> predicate){
		return new PredicatePermissionImpl<>(predicate);
	}

	/**
	 * Returns the predicate for this permission.
	 * @return predicate
	 */
	@NotNull
	Predicate<C> getPredicate();
}
