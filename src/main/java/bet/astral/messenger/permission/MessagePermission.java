package bet.astral.messenger.permission;

import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true)
public class MessagePermission implements Permission {
	private final String permission;

	@Contract(pure = true)
	public MessagePermission(String permission) {
		this.permission = permission;
	}

	@Override
	public boolean checkPermission(@NotNull CommandSender commandSender) {
		return commandSender.hasPermission(permission != null ? permission : "");
	}

	@Override
	public @NonNull String permissionString() {
		return permission;
	}
}
