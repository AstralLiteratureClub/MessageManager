package bet.astral.messenger.database.message;

import bet.astral.messenger.database.MessageDatabase;
import bet.astral.messenger.offline.OfflineMessage;
import bet.astral.messenger.offline.ParsedOfflineMessage;
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
