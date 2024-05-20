package bet.astral.messenger.v2;

import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.info.MessageInfo;
import bet.astral.messenger.v2.info.MessageInfoBuilder;
import bet.astral.messenger.v2.info.MultiMessageInfo;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.PlaceholderLoader;
import bet.astral.messenger.v2.placeholder.hooks.PlaceholderHookManager;
import bet.astral.messenger.v2.receiver.ForwardingReceiver;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import bet.astral.platform.Platform;
import bet.astral.platform.entity.Permissionable;
import bet.astral.platform.permission.Permission;
import bet.astral.platform.scheduler.delay.Delay;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.function.Function;

/**
 * Represents a messenger which translates and is the handler of placeholder parsing in messages.
 */
public interface Messenger extends Randomly {
	/**
	 * Creates a new instance of a messenger.
	 * @param logger logger
	 * @return messenger
	 */
	static Messenger of(Logger logger){
//		return new MessengerImpl(logger);
		return null;
	}

	/**
	 * Returns the translation key registry.
	 * @return translation key registry
	 */
	@NotNull
	TranslationKeyRegistry getTranslationKeyRegistry();

	void setPlaceholderHookManager(@NotNull PlaceholderHookManager hookManager);
	@NotNull PlaceholderHookManager getPlaceholderHookManager();
	void setPlaceholderLoader(@NotNull PlaceholderLoader loader);
	@NotNull PlaceholderLoader getPlaceholderLoader();

	/**
	 * Returns true if the messages are being sent async
	 * @return async, else false
	 */
	boolean sendMessagesASync();

	/**
	 * Returns if the messenger tries to use the receiver locale before resorting to the default locale.
	 * @return true if try to use receiver's locale, else false
	 */
	boolean tryToUseReceiverLocale();

	/**
	 * Returns the default locale
	 * @return locale
	 */
	@NotNull
	Locale getLocale();

	/**
	 * Parses given message info. Does not use receiver to try to use placeholder hooks in the given message.
	 * @param messageInfo message info
	 * @return component if could parse, else null
	 */
	@Nullable
	default Component parseComponent(@NotNull MessageInfo messageInfo, @NotNull ComponentType componentType) {
		return parseComponent(messageInfo, componentType, false);
	}

	/**
	 * Parses given message info and depending on if messenger info is used uses receiver to parse message
	 * @param messageInfo message info
	 * @param useMessageInfoReceivers should use message info receiver to help the parsing message info
	 * @return component if could parse, else null
	 */
	@Nullable
	Component parseComponent(@NotNull MessageInfo messageInfo, @NotNull ComponentType componentType, boolean useMessageInfoReceivers);

	/**
	 * Parses given the translation key using given locale and placeholders.
	 * @param translationKey translation key
	 * @param locale locale
	 * @param placeholders placeholders
	 * @return component if could parse, else null
	 */
	@Nullable
	default Component parseComponent(@NotNull TranslationKey translationKey, @NotNull Locale locale, @NotNull ComponentType componentType, @NotNull Placeholder... placeholders) {
		return parseComponent(createMessage(translationKey).withLocale(locale).addPlaceholders(placeholders).create(), componentType);
	}

	void send(@NotNull MessageInfo... messageInformation) throws ClassCastException;
	void send(@NotNull MessageInfoBuilder... messageInformation) throws ClassCastException;
	void send(@NotNull MultiMessageInfo... multiMessageInformation) throws ClassCastException;
	@NotNull
	MessageInfoBuilder createMessage(@NotNull TranslationKey translation);
	void broadcast(@Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void broadcast(@Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void broadcast(@Nullable Permission<Receiver> permission, @NotNull TranslationKey translation);
	void broadcast(@Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation);
	void broadcast(@NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void broadcast(@NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void broadcast(@NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation);
	void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation);
	void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation) throws ClassCastException;
	void message(@NotNull Object receiver, @NotNull TranslationKey translation, Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException;

	/**
	 * Returns the console and players of the server to broadcast messages to
	 * @return combined receiver
	 */
	default Receiver broadcast() {
		Collection<Receiver> receivers = new LinkedList<>(Platform.getPlatform().getServer().getPlayers());
		receivers.add(Platform.getPlatform().getServer().getConsole());
		return Receiver.of(receivers);
	}

	/**
	 * Returns the console and players if the given permission returns true on given receiver
	 * @param permission permission
	 * @return combined receiver
	 */
	default Receiver broadcast(@NotNull Permission<Permissionable> permission) {
		Collection<Receiver> receivers = new LinkedList<>();
		if (console().hasPermission(permission)){
			receivers.add(console());
		}
		receivers.addAll(Platform.getPlatform().getServer().getPlayers().stream().filter(player->player.hasPermission(permission)).toList());
		return Receiver.of(receivers);
	}

	/**
	 * Returns the console receiver
	 * @return console receiver
	 */
	default Receiver console() {
		return Platform.getPlatform().getServer().getConsole();
	}

	/**
	 * Registers a new receiver converter to the messenger
	 * @param converter converter
	 */
	void registerReceiverConverter(Function<Object, Receiver> converter);

	/**
	 * Converts the given object to an object.
	 * Returns null if none of the converters could convert the object to a receiver
	 * @param object object to convert
	 * @return converter
	 */
	@Nullable
	Receiver convertReceiver(@NotNull Object object);

	/**
	 * Returns logger of this messenger
	 * @return logger
	 */
	Logger getLogger();
}
