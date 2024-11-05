package bet.astral.messenger.v3.minecraft.paper;

import bet.astral.messenger.v2.AbstractMessenger;
import bet.astral.messenger.v2.DefaultScheduler;
import bet.astral.messenger.v3.minecraft.paper.receiver.ConsoleReceiver;
import bet.astral.messenger.v3.minecraft.paper.scheduler.ASyncScheduler;
import bet.astral.messenger.v2.receiver.Receiver;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PaperMessenger extends AbstractMessenger {
	public static Plugin PLUGIN = null;
	public static PlayerManager playerManager;

	public static void init(Plugin plugin){
		if (PLUGIN != null && PLUGIN.isEnabled()){
			return;
		}

		try {
			new PaperPlatform();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			plugin.getComponentLogger().error("Tried to initialize paper platform, but encountered an exception", e);
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}

		DefaultScheduler.ASYNC_SCHEDULER = ASyncScheduler.SCHEDULER;
		PLUGIN = plugin;
		playerManager = new PlayerManager();
		plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
	}

	public PaperMessenger(Logger logger) {
		super(logger);
		registerReceiverConverter(o->{
			if (o instanceof Player player) {
				return playerManager.players.get(player.getUniqueId());
			} else if (o instanceof CommandSender sender){
				if (sender instanceof ConsoleCommandSender consoleCommandSender) {
					return console();
				}
			} else if (o instanceof Receiver receiver){
				return receiver;
			}
			return null;
		});
	}

	public PaperMessenger(Logger logger, Random random) {
		super(logger, random);
	}

	@Override
	public List<Receiver> getPlayers() {
		return new LinkedList<>(playerManager.players.values());
	}

	public PlayerManager getPlayerManager(){
		return playerManager;
	}

	@Override
	public Receiver console() {
		return ConsoleReceiver.CONSOLE_RECEIVER;
	}
}
