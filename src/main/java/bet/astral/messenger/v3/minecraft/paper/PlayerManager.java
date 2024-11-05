package bet.astral.messenger.v3.minecraft.paper;

import bet.astral.messenger.v3.minecraft.paper.receiver.PlayerReceiver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager implements Listener {
	public final Map<UUID, PlayerReceiver> players = new HashMap<>();

	public PlayerManager() {
	}


	@EventHandler(priority = EventPriority.HIGHEST)
	private void onJoin(PlayerJoinEvent event){
		players.put(event.getPlayer().getUniqueId(), new PlayerReceiver(event.getPlayer()));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event){
		players.remove(event.getPlayer().getUniqueId());
	}
}
