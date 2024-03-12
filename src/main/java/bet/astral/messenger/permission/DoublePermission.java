package bet.astral.messenger.permission;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;

@Getter
@Deprecated(forRemoval = true)
public class DoublePermission implements Permission {
	private final Permission one;
	private final Permission two;

	@Contract(pure = true)
	public DoublePermission(Permission one, Permission two) {
		this.one = one;
		this.two = two;
	}

	@Override
	public boolean checkPermission(CommandSender commandSender) {
		return one.checkPermission(commandSender) && two.checkPermission(commandSender);
	}

	@Override
	public @NonNull String permissionString() {
		return one.permissionString()+", "+two.permissionString();
	}
}