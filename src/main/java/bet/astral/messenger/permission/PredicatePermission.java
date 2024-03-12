package bet.astral.messenger.permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.permission.PermissionResult;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@Deprecated(forRemoval = true)
public class PredicatePermission implements org.incendo.cloud.permission.PredicatePermission, Permission {
	private final Predicate<CommandSender> predicate;

	@Contract(pure = true)
	public PredicatePermission(Predicate<CommandSender> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean checkPermission(CommandSender commandSender) {
		return predicate.test(commandSender);
	}

	@Override
	public @NonNull PermissionResult testPermission(@NotNull Object sender) {
		if (sender instanceof CommandSender commandSender){
			return PermissionResult.of(this.checkPermission(commandSender), this);
		}
		return PermissionResult.denied(this);
	}
}
