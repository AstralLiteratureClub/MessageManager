package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.CompletableFuture;

/**
 * Completable future permission to remove the permission checking off the main thread.
 * This permission might not be implemented in every permission system!
 * @version 1.0
 * @param <C>
 */
@ApiStatus.Experimental
public interface FuturePermission<C extends Permissionable> extends Permission<C>{
	/**
	 * A non-blocking way to test a permission off the main thread.
	 * @param permissionable permission
	 * @return completable future, with the permission test result
	 */
	CompletableFuture<Boolean> testFuture(C permissionable);
}
