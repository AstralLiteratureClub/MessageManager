package bet.astral.messenger.v2.permission;

import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.CompletableFuture;

/**
 * Completable future permission to remove the permission checking off the main thread.
 * This permission might not be implemented in every permission system!
 * @version 1.0
 */
@ApiStatus.Experimental
public interface FuturePermission extends Permission{
	/**
	 * A non-blocking way to test a permission off the main thread.
	 * @param permissionable permission
	 * @return completable future, with the permission test result
	 */
	default CompletableFuture<Boolean> testFuture(Permissionable permissionable) {
		return CompletableFuture.supplyAsync(()->test(permissionable));
	}
}
