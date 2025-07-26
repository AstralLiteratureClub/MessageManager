package bet.astral.messenger.v3.minecraft.bukkit;

import bet.astral.messenger.v2.Platform;
import bet.astral.messenger.v3.minecraft.bukkit.receiver.ConsoleReceiver;
import bet.astral.messenger.v2.receiver.AudienceReceiver;
import bet.astral.messenger.v2.receiver.Receiver;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;

public class BukkitPlatform extends Platform {
	private final BukkitAudiences audiences;
	protected BukkitPlatform(BukkitAudiences bukkitAudiences) throws NoSuchFieldException, IllegalAccessException {
		super("messenger-bukkit", ConsoleReceiver.CONSOLE_RECEIVER);
		Field field = Platform.class.getDeclaredField("platform");
		field.setAccessible(true);
		field.set(null, this);
		this.audiences = bukkitAudiences;
	}

	@Override
	public @NotNull Collection<@NotNull Receiver> getPlayers() {
		return toReceiversPlayer(Bukkit.getOnlinePlayers());
	}

	public @NotNull Collection<? extends @NotNull Audience> getPlayersAsAudiences(){
		Collection<Audience> audiences = new LinkedList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			// Might be used in bukkit servers so just make it simple and allow non audience members to be converted.
			if (!(player instanceof Audience)) {
				audiences.add(this.audiences.player(player));
			}
			audiences.add(player);
			continue;
		}
		return audiences;
	}
	public Audience toAudience(CommandSender sender){
		return audiences.sender(sender);
	}
	public Receiver toReceiver(CommandSender sender){
		if (sender instanceof Player player){
			return BukkitMessenger.playerManager.players.get(player.getUniqueId());
		} else if (sender instanceof ConsoleCommandSender){
			return ConsoleReceiver.CONSOLE_RECEIVER;
		}else {
			return new AudienceReceiver(toAudience(sender));
		}
	}
	public Collection<Receiver> toReceiversPlayer(@NotNull Collection<? extends Player> players){
		Collection<Receiver> receivers = new LinkedList<>();
		for (Player player : players) {
			receivers.add(BukkitMessenger.playerManager.players.get(player.getUniqueId()));
		}
		return receivers;
	}
	public Collection<Receiver> toReceivers(@NotNull Collection<? extends Audience> audiences){
		Collection<Receiver> receivers = new LinkedList<>();
		for (Audience audience : audiences){
			if (audience instanceof Player player){
				receivers.add(BukkitMessenger.playerManager.players.get(player.getUniqueId()));
			} else if (audience instanceof ConsoleCommandSender){
				receivers.add(ConsoleReceiver.CONSOLE_RECEIVER);
			}else {
				receivers.add(new AudienceReceiver(audience));
			}
		}
		return receivers;
	}
}
