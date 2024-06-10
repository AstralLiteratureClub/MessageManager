package bet.astral.messenger.v2.paper;

import bet.astral.messenger.v2.AbstractMessenger;
import bet.astral.messenger.v2.paper.receiver.ConsoleReceiver;
import bet.astral.messenger.v2.receiver.Receiver;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PaperMessenger extends AbstractMessenger {
	public static Plugin PLUGIN = null;
	private static PlayerManager playerManager;
	public static void init(Plugin plugin){
		if (PLUGIN != null && PLUGIN.isEnabled()){
			return;
		}
		PLUGIN = plugin;
		playerManager = new PlayerManager();
		plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
	}
	public PaperMessenger(Logger logger) {
		super(logger);
	}

	public PaperMessenger(Logger logger, Random random) {
		super(logger, random);
	}

	@Override
	public List<Receiver> getPlayers() {
		return new LinkedList<>(playerManager.players.values());
	}

	@Override
	public Receiver console() {
		return ConsoleReceiver.CONSOLE_RECEIVER;
	}
}
