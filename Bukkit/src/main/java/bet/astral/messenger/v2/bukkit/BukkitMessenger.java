package bet.astral.messenger.v2.bukkit;

import bet.astral.messenger.v2.AbstractMessenger;
import bet.astral.messenger.v2.DefaultScheduler;
import bet.astral.messenger.v2.bukkit.receiver.ConsoleReceiver;
import bet.astral.messenger.v2.bukkit.scheduler.GlobalASyncScheduler;
import bet.astral.messenger.v2.receiver.Receiver;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BukkitMessenger extends AbstractMessenger {
	public static Plugin PLUGIN = null;
	public static PlayerManager playerManager;

	public static void init(Plugin plugin, BukkitAudiences bukkitAudiences){
		if (PLUGIN != null && PLUGIN.isEnabled()){
			return;
		}

		try {
			new BukkitPlatform(bukkitAudiences);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			plugin.getLogger().severe("Tried to initialize bukkit platform, but encountered an exception");
			plugin.getLogger().severe(e.toString());
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}

		DefaultScheduler.ASYNC_SCHEDULER = GlobalASyncScheduler.SCHEDULER;
		PLUGIN = plugin;
		playerManager = new PlayerManager();
		plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
	}

	public BukkitMessenger(Logger logger) {
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

	public BukkitMessenger(Logger logger, Random random) {
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
