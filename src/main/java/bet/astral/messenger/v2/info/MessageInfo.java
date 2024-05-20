package bet.astral.messenger.v2.info;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.PlaceholderList;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.platform.annotations.Immutable;
import bet.astral.platform.permission.Permission;
import bet.astral.platform.scheduler.delay.Delay;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Builder value for sending a message to a receiver
 * Can be used to send a message to a receiver using a messenger.
 */
@Immutable
public interface MessageInfo {
	/**
	 * Returns the translation key for the message which is being sent out.
	 * @return translation
	 */
	@NotNull
	TranslationKey getTranslationKey();

	/**
	 * Returns the extra placeholders for given the message.
	 * @return placeholders
	 */
	@NotNull
	@Immutable
	Map<String, Placeholder> getPlaceholders();

	/**
	 * Returns the required permission to see this message
	 * @return permission
	 */
	@NotNull
	Permission<Receiver> getPermission();

	/**
	 * Returns the locale this message is being sent using
	 * @return locale
	 */
	@Nullable
	Locale getLocale();

	/**
	 * Returns if the messenger tries to find messages based on players locale
	 * @return true if messenger should try to find messages based on sender's locale.
	 */
	boolean tryToUseReceiverLocale();

	/**
	 * Returns the delay this message is being sent on.
	 * @return delay
	 */
	@NotNull
	Delay getDelay();

	/**
	 * Returns the collection of receivers as objects.
	 * Converts objects to receivers using {@link com.google.common.base.Converter}.
	 * @return receivers of this message
	 */
	@NotNull
	@Immutable
	Collection<Object> getReceivers();

	/**
	 * Returns the collection of receivers as parsed and combined receiver
	 * @param messenger messenger to parse receivers
	 * @return receiver
	 */
	@NotNull
	default Receiver getReceiversAsReceiver(@NotNull Messenger messenger) {
		Collection<Receiver> receivers = new LinkedList<>();
		for (Object receiverObj : getReceivers()){
			Receiver receiver = messenger.convertReceiver(receiverObj);
			if (receiver == null){
				continue;
			}
			receivers.add(receiver);
		}
		return Receiver.of(receivers);
	}

	/**
	 * Returns the translation key using the given messenger and given receiver.
	 * Receiver is nullable uses cached locale if no messenger is found!
	 * Returns null if it couldn't parse the message as a component.
	 * @param messenger messenger
	 * @param receiver receiver
	 * @return parsed as component
	 */
	@Nullable
	default Component parseAsComponent(@NotNull Messenger messenger, @NotNull  Receiver receiver, @NotNull ComponentType componentType) {
		Locale locale = receiver.getLocale();
		if (locale == null){
			locale = getLocale();
			if (locale == null){
				return null;
			}
		}
		return messenger.parseComponent(toBuilder().withLocale(locale).withReceiver(receiver).create(), componentType, true);
	}

	/**
	 * Parses the translation key using the given messenger and using the locale provided.
	 * Returns null if it couldn't parse the message as a component.
	 * @param messenger messenger
	 * @param locale locale
	 * @return parsed as component
	 */
	default @Nullable Component parseAsComponent(@NotNull Messenger messenger, @NotNull Locale locale, @NotNull ComponentType componentType) {
		return messenger.parseComponent(toBuilder().withLocale(locale).create(), componentType, false);
	}

	/**
	 * Parses the translation key using the given messenger and uses the locale provided by {@link #getLocale()}
	 * Returns null if it couldn't parse the message as a component.
	 * @param messenger messenger
	 * @return parsed as component
	 */
	@Nullable
	default Component parseAsComponent(@NotNull Messenger messenger, @NotNull ComponentType componentType){
		Locale locale = getLocale();
		if (locale == null){
			return null;
		}
		return parseAsComponent(messenger, locale, componentType);
	}

	@NotNull
	default MessageInfoBuilder toBuilder(){
		return new MessageInfoBuilder(getTranslationKey())
				.withPermission(getPermission())
				.withDelay(getDelay())
				.withReceivers(getReceivers())
				.addPlaceholders(PlaceholderList.toList(getPlaceholders()))
				.useReceiverDelay(tryToUseReceiverLocale());

	}
}
