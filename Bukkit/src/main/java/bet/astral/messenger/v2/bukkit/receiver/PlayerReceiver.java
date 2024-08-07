package bet.astral.messenger.v2.bukkit.receiver;

import bet.astral.messenger.v2.bukkit.BukkitPlatform;
import bet.astral.messenger.v2.bukkit.scheduler.GlobalScheduler;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.task.IScheduler;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PlayerReceiver implements Receiver, ForwardingAudience {
	private final UUID uniqueId;

	public PlayerReceiver(Player player) {
		this.uniqueId = player.getUniqueId();
	}
	@Override
	public @NotNull IScheduler getScheduler() {
		return GlobalScheduler.SCHEDULER;
	}

	@Override
	public @NotNull Locale getLocale() {
		return new Locale(getPlayer().getLocale());
	}

	public @Nullable Player getPlayer(){
		return Bukkit.getPlayer(uniqueId);
	}

	public @NotNull UUID getUniqueId(){
		return uniqueId;
	}

	@Override
	public boolean hasPermission(@NotNull Permission permission) {
		return permission.test(this);
	}

	@Override
	public boolean hasPermission(@NotNull String s) {
		return Bukkit.getPlayer(uniqueId).hasPermission(s);
	}

	@Override
	public @NotNull Iterable<? extends Audience> audiences() {
		return List.of(((BukkitPlatform) BukkitPlatform.getPlatform()).toReceiver(getPlayer()));
	}
}













