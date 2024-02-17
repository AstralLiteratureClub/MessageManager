package bet.astral.messenger.database.player;

import bet.astral.messenger.database.PlayerDatabase;
import bet.astral.messenger.offline.OfflineMessageProfile;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractPlayerDatabase<P extends JavaPlugin> implements PlayerDatabase<P> {
	private final P plugin;

	protected AbstractPlayerDatabase(P plugin) {
		this.plugin = plugin;
	}

	@Override
	public P plugin() {
		return plugin;
	}


	@Override
	public @NotNull CompletableFuture<@NotNull OfflineMessageProfile> loadAndDelete(@NotNull UUID id) {
		return null;
	}

	@Override
	public @NotNull CompletableFuture<@NotNull OfflineMessageProfile> loadAndDelete(@NotNull OfflinePlayer player) {
		return null;
	}
}
