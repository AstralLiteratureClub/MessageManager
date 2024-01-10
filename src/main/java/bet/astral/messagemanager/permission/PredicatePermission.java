package bet.astral.messagemanager.permission;

import org.bukkit.command.CommandSender;

import java.util.function.Predicate;

public class PredicatePermission implements Permission{
	private final Predicate<CommandSender> predicate;

	public PredicatePermission(Predicate<CommandSender> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean checkPermission(CommandSender commandSender) {
		return predicate.test(commandSender);
	}
}
