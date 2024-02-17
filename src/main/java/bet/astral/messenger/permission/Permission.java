package bet.astral.messenger.permission;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public interface Permission {
	Permission empty = Permission.of("");
	boolean checkPermission(CommandSender commandSender);

	default Permission with(String permission) {
		return new DoublePermission(this, Permission.of(permission));
	}
	default Permission with(Permission permission) {
		return new DoublePermission(this, permission);
	}
	default Permission with(Predicate<CommandSender> predicate) {
		return new DoublePermission(this, new PredicatePermission(predicate));
	}

	@NotNull
	default Collection<Permission> permissions() {
		Collection<Permission> permissions = new ArrayList<>();
		if (this instanceof DoublePermission doublePermission){
			permissions.addAll(doublePermission.getOne().permissions());
			permissions.addAll(doublePermission.getTwo().permissions());
		} else {
			permissions.add(this);
		}
		return permissions;
	}


	@Contract(value = "_ -> new", pure = true)
	static @NotNull Permission of(String permission){
		return new MessagePermission(permission);
	}
	@Contract(value = "_, _ -> new", pure = true)
	static @NotNull Permission of(Permission permission, Permission permission2){
		return new DoublePermission(permission, permission2);
	}
	@Contract(value = "_ -> new", pure = true)
	static @NotNull Permission of(Predicate<CommandSender> predicate){
		return new PredicatePermission(predicate);
	}

	@Contract("_, _ -> new")
	static @NotNull Permission of(Predicate<CommandSender> predicate, Predicate<CommandSender> predicate2){
		return new DoublePermission(new PredicatePermission(predicate), new PredicatePermission(predicate2));
	}

	static Permission of(String @NotNull ... permissions){
		Permission permission = empty;
		for (String permissionStr : permissions){
			permission = permission.with(permissionStr);
		}
		return permission;
	}
	static Permission of(Permission @NotNull ... permissions){
		Permission permission = empty;
		for (Permission permissionStr : permissions){
			permission = permission.with(permissionStr);
		}
		return permission;
	}
	@SafeVarargs
	static Permission of(Predicate<CommandSender> @NotNull ... predicates){
		Permission permission = empty;
		for (Predicate<CommandSender> predicate : predicates){
			permission = permission.with(predicate);
		}
		return permission;
	}
}
