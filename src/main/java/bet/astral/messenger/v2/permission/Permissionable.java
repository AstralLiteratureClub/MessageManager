package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

public interface Permissionable {
	boolean hasPermission(@NotNull Permission<?> permission);
	boolean hasPermission(@NotNull String permission);
}
