package bet.astral.messenger.v3.minecraft.paper;

import bet.astral.messenger.v2.Platform;
import bet.astral.messenger.v3.minecraft.paper.receiver.ConsoleReceiver;
import bet.astral.messenger.v2.receiver.AudienceReceiver;
import bet.astral.messenger.v2.receiver.Receiver;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;

public class PaperPlatform extends Platform {
	protected PaperPlatform() throws NoSuchFieldException, IllegalAccessException {
		super("messenger-paper", ConsoleReceiver.CONSOLE_RECEIVER);
		Field field = Platform.class.getDeclaredField("platform");
		field.setAccessible(true);
		field.set(null, this);
	}

	@Override
	public @NotNull Collection<@NotNull Receiver> getPlayers() {
		return toReceivers(Bukkit.getOnlinePlayers());
	}

	public Collection<Receiver> toReceivers(@NotNull Collection<? extends Audience> audiences){
		Collection<Receiver> receivers = new LinkedList<>();
		for (Audience audience : audiences){
			if (audience instanceof Player player){
				receivers.add(PaperMessenger.playerManager.players.get(player.getUniqueId()));
			} else if (audience instanceof ConsoleCommandSender consoleCommandSender){
				receivers.add(ConsoleReceiver.CONSOLE_RECEIVER);
			}else {
				receivers.add(new AudienceReceiver(audience));
			}
		}
		return receivers;
	}
}
