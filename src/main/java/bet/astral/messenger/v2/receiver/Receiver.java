package bet.astral.messenger.v2.receiver;

import bet.astral.messenger.v2.permission.Permissionable;
import bet.astral.messenger.v2.task.IScheduler;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

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
	@Contract(value = "_ -> new", pure = true)
	static @NonNull ForwardingReceiver ofReceivers(@NotNull Collection<? extends Receiver> receivers){
		return new ForwardingReceiverImpl(receivers);
	}
	/**
	 * Returns a forwarding receiver for all given receivers
	 * @param receivers receivers
	 * @return combined receiver
	 */
	@Contract(value = "_ -> new", pure = true)
	static @NonNull ForwardingReceiver of(@NotNull Receiver... receivers){
		return new ForwardingReceiverImpl(receivers);
	}

	/**
	 * Creates a receiver which the messenger can process.
	 * @param audience audience
	 * @return receiver
	 */
	@Contract(value = "_ -> new", pure = true)
	static @NonNull Receiver ofAudiences(@NotNull Collection<? extends Audience> audience){
		return new AudienceReceiver(audience);
	}
	/**
	 * Creates a receiver which the messenger can process.
	 * @param audience audience
	 * @return receiver
	 */
	@Contract(value = "_ -> new", pure = true)
	static @NonNull Receiver of(@NotNull Audience... audience){
		return new AudienceReceiver(audience);
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
