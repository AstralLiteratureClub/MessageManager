package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Predicate permission checks the given predicate, and if the predicate returns true then the permission returns true
 */
public interface PredicatePermission extends Permission{
	/**
	 * Creates a new instance of predicate permission
	 * @param predicate predicate
	 * @return predicate permission
	 */
	@NotNull
	static Permission of(@NotNull Predicate<Permissionable> predicate){
		return new PredicatePermissionImpl(predicate);
	}

	/**
	 * Returns the predicate for this permission.
	 * @return predicate
	 */
	@NotNull
	Predicate<Permissionable> getPredicate();
}
