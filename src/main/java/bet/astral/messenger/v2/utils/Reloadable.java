package bet.astral.messenger.v2.utils;

import java.util.concurrent.CompletableFuture;

/**
 * Resembles a class which can be reloaded during runtime to change values.
 */
public interface Reloadable {
	void reload();
	CompletableFuture<Void> reloadASync();
}
