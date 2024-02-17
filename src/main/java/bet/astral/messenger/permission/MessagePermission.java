package bet.astral.messenger.permission;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
}
