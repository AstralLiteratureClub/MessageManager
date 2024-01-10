package bet.astral.messagemanager.database;

import bet.astral.messagemanager.offline.OfflineMessageProfile;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlayerDatabase<P extends JavaPlugin> {
	/**
	 * Returns the plugin for this database
	 * @return plugin
	 */
	P plugin();

	void save(@NotNull OfflinePlayer player, @NotNull OfflineMessageProfile profile);

	@NotNull
	CompletableFuture<@NotNull OfflineMessageProfile> load(@NotNull UUID id);
	@NotNull
	CompletableFuture<@NotNull OfflineMessageProfile> load(@NotNull OfflinePlayer player);

	@NotNull
	CompletableFuture<@NotNull OfflineMessageProfile> loadAndDelete(@NotNull UUID id);
	@NotNull
	CompletableFuture<@NotNull OfflineMessageProfile> loadAndDelete(@NotNull OfflinePlayer player);
	void delete(OfflinePlayer player);


	@Contract(pure = true)
	static <T extends JavaPlugin> @Nullable PlayerDatabase<T> createDatabaseInstance(T plugin, DatabaseType type) throws IllegalStateException{
		if (!type.driverFound){
			throw new IllegalStateException("Could not find drivers for new message database instance! No class found: "+ type.getDriverClass() + "; Is local: "+ type.isLocalFile());
		}
		switch (type){
			case H2 -> {
			}
			case MYSQL -> {
			}
			case SQLITE -> {
			}
			case POSTGRESQL -> {
			}
		}
		return null;
	}
}