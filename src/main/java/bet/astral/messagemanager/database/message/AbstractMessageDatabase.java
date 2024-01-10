package bet.astral.messagemanager.database.message;

import bet.astral.messagemanager.database.MessageDatabase;
import bet.astral.messagemanager.offline.OfflineMessage;
import bet.astral.messagemanager.offline.ParsedOfflineMessage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class AbstractMessageDatabase<P extends JavaPlugin> implements MessageDatabase<P> {
	private final P plugin;

	protected AbstractMessageDatabase(P plugin) {
		this.plugin = plugin;
	}

	@Override
	public P plugin() {
		return plugin;
	}

	@Override
	public CompletableFuture<String> update(String hashedMessageKey, Consumer<OfflineMessage> offlineMessage) {
		return null;
	}
	@Override
	public CompletableFuture<ParsedOfflineMessage> loadAndDelete(String hashedMessageId) {
		return null;
	}
}
