package bet.astral.messenger.adventure;

import bet.astral.messenger.MessageReceiver;
import bet.astral.messenger.placeholder.Placeholderable;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.plugin.java.JavaPlugin;

public interface MessengerAudience<P extends JavaPlugin> extends ForwardingAudience, Placeholderable, MessageReceiver<P> {
	@Override
	default Audience getAudience() {
		return this;
	}
}
