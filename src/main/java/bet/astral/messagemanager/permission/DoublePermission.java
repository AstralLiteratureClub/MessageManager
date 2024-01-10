package bet.astral.messagemanager.permission;

import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public class DoublePermission implements Permission {
	private final Permission one;
	private final Permission two;

	public DoublePermission(Permission one, Permission two) {
		this.one = one;
		this.two = two;
	}

	@Override
	public boolean checkPermission(CommandSender commandSender) {
		return one.checkPermission(commandSender) && two.checkPermission(commandSender);
	}
}