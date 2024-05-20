package bet.astral.messenger.v2.receiver;

import bet.astral.platform.entity.Permissionable;
import bet.astral.platform.scheduler.schedulers.IEntityScheduler;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Locale;

/**
 * Receivers is the messenger audience receiver
 */
public interface Receiver extends Audience, Permissionable {
	/**
	 * Returns a forwarding receiver for all given receivers
	 * @param receivers receivers
	 * @return combined receiver
	 */
	static ForwardingReceiver of(@NotNull Collection<Receiver> receivers){
		return new ForwardingReceiverImpl(receivers);
	}
	/**
	 * Gets the entity scheduler for the given receiver
	 * @return scheduler
	 */
	@NotNull
	IEntityScheduler getEntityScheduler();
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
