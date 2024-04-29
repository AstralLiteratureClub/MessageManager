package bet.astral.messenger.adventure;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface PlayerMessengerAudience<P extends JavaPlugin> extends MessengerAudience<P> {
	@NotNull
	default OfflinePlayer asOfflinePlayer() {
		return Bukkit.getOfflinePlayer(getUniqueId());
	}
	@Nullable
	default Player asPlayer() {
		return Bukkit.getPlayer(getUniqueId());
	}
	@NotNull
	UUID getUniqueId();
}
