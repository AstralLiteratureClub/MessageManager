package bet.astral.messagemanager.permission;

import org.bukkit.command.CommandSender;

public class MessagePermission implements Permission {
	private final String permission;

	public MessagePermission(String permission) {
		this.permission = permission;
	}

	@Override
	public boolean checkPermission(CommandSender commandSender) {
		return commandSender.hasPermission(permission);
	}
}
