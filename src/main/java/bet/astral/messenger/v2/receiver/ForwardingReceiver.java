package bet.astral.messenger.v2.receiver;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Works the same as forwarding audience from adventure. Forwards messages received to the sub receivers.
 */
public interface ForwardingReceiver extends Receiver, ForwardingAudience {
	/**
	 * Returns the receivers this forwarder is super to
	 * @return sub receivers
	 */
	@NotNull Collection<? extends Receiver> getReceivers();

	/**
	 * Returns the receivers this receiver is super to
	 * @return sub receivers
	 */
	@Override
	default @NotNull Iterable<? extends Audience> audiences() {
		return getReceivers();
	}

	@Override
	default boolean isLocaleSupported() {
		return false;
	}
}
