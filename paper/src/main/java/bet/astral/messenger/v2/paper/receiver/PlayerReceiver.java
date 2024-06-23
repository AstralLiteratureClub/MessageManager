package bet.astral.messenger.v2.paper.receiver;

import bet.astral.messenger.v2.paper.scheduler.ASyncScheduler;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.receiver.Forwarder;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.task.IScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.UUID;

public class PlayerReceiver implements Receiver {
	private final UUID uniqueId;

	public PlayerReceiver(Player player) {
		this.uniqueId = player.getUniqueId();
	}
	@Override
	public @NotNull IScheduler getScheduler() {
		return ASyncScheduler.SCHEDULER;
	}

	@Override
	public @NotNull Locale getLocale() {
		return Bukkit.getPlayer(uniqueId).locale();
	}

	@Override
	public boolean hasPermission(@NotNull Permission permission) {
		return permission.test(this);
	}

	@Override
	public boolean hasPermission(@NotNull String s) {
		return Bukkit.getPlayer(uniqueId).hasPermission(s);
	}
}