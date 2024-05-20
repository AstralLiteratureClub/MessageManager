package bet.astral.messenger.v2.receiver;

import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Works the same as adventure audiences, forwards messages to the next person
 */
public interface ForwardingForwarder extends ForwardingReceiver, Forwarder, ForwardingAudience {
	/**
	 * Returns the receivers
	 * @return receivers
	 */
	@Override
	@NotNull Collection<? extends Forwarder> getReceivers();
}
