package bet.astral.messenger.v2.receiver;

import bet.astral.messenger.v2.permission.Permissionable;
import bet.astral.messenger.v2.task.IScheduler;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Locale;

/**
 * Receivers is the messenger audience receiver
 */
public interface Receiver extends ForwardingAudience, Permissionable {
	/**
	 * Returns a forwarding receiver for all given receivers
	 * @param receivers receivers
	 * @return combined receiver
	 */
	static ForwardingReceiver of(@NotNull Collection<Receiver> receivers){
		return new ForwardingReceiverImpl(receivers);
	}

	/**
	 * Returns an empty receiver which doesn't forward messages or anything just acts as an empty receiver.
	 * @return empty receiver
	 */
	static Receiver empty(){
		return EmptyReceiverImpl.emptyReceiver;
	}

	/**
	 * Gets the entity scheduler for the given receiver
	 * @return scheduler
	 */
	@NotNull
	IScheduler getScheduler();
	/**
	 * Returns the locale of the receiver
	 * @return returns the locale of the receiver
	 */
	@NotNull
	Locale getLocale();

	/**
	 * Returns true if using {@link #getLocale()} is supported.
	 * @return true if supported, else false
	 */
	default boolean isLocaleSupported(){
		return true;
	}
}
