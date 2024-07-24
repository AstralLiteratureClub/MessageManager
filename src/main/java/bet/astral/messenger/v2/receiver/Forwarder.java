package bet.astral.messenger.v2.receiver;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

/**
 * Forwarder is just a receiver
 * which has the ability to send messages directly to the receiver without using a messenger.
 */
public interface Forwarder extends Receiver{
	/**
	 * Returns the messenger which is used to send messages to this message forwarder
	 * @return messenger
	 */
	@NotNull
	Messenger getMessenger();

	/**
	 * Sends a message to this receiver if the forwarder has given permission. 
	 * @param permission permission to receive
	 * @param translation translation key
	 * @param placeholdersCollection placeholders collection
	 * @param placeholders placeholders array
	 */
	default void message(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<Placeholder> placeholdersCollection, @NotNull Placeholder... placeholders) {
		message(permission, Delay.NONE, translation, placeholdersCollection, placeholders);
	}
	/**
	 * Sends a message to this receiver if the forwarder has given permission.
	 * Delays the message by X amount of time before sending it
	 * @param permission permission to receive
	 * @param delay delay before sending the given message
	 * @param translation translation key
	 * @param placeholdersCollection placeholders
	 * @param placeholders placeholders array
	 */
	default void message(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<Placeholder> placeholdersCollection, @NotNull Placeholder... placeholders) {
		getMessenger().message(this, permission, delay, translation, placeholdersCollection, placeholders);
	}
	/**
	 * Sends a message to this receiver if the forwarder has given permission.
	 * @param permission permission to receive
	 * @param translation translation key
	 * @param placeholders placeholders array
	 */
	default void message(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		message(permission, Delay.NONE, translation, Collections.emptyList(), placeholders);
	}
	/**
	 * Sends a message to this receiver if the forwarder has given permission.
	 * Delays the message by X amount of time before sending it
	 * @param permission permission to receive
	 * @param delay delay before sending the given message
	 * @param translation translation key
	 * @param placeholders placeholders array
	 */
	default void message(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		message(permission, delay, translation, Collections.emptyList(), placeholders);
	}
	/**
	 * Sends a message to this receiver if the forwarder has given permission.
	 * @param permission permission to receive
	 * @param translation translation key
	 * @param placeholdersCollection placeholders
	 */
	default void message(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<Placeholder> placeholdersCollection) {
		message(permission, Delay.NONE, translation, placeholdersCollection);
	}
	/**
	 * Sends a message to this receiver if the forwarder has given permission.
	 * Delays the message by X amount of time before sending it
	 * @param permission permission to receive
	 * @param delay delay before sending the given message
	 * @param translation translation key
	 * @param placeholdersCollection placeholders
	 */
	default void message(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<Placeholder> placeholdersCollection) {
		message(permission, delay, translation, placeholdersCollection, new Placeholder[0]);
	}
	/**
	 * Sends a message to this receiver if the forwarder has given permission.
	 * @param permission permission to receive
	 * @param translation translation key
	 */
	default void message(@Nullable Permission permission, @NotNull TranslationKey translation) {
		message(permission, Delay.NONE, translation, Collections.emptyList(), new Placeholder[0]);

	}
	/**
	 * Sends a message to this receiver if the forwarder has given permission.
	 * Delays the message by X amount of time before sending it
	 * @param permission permission to receive
	 * @param delay delay before sending the given message
	 * @param translation translation key
	 */
	default void message(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation) {
		message(permission, delay, translation, Collections.emptyList(), new Placeholder[0]);
	}

	/**
	 * Sends a message to this receiver.
	 * @param translation translation key
	 * @param placeholdersCollection placeholders
	 * @param placeholders placeholders
	 */
	default void message(@NotNull TranslationKey translation, @NotNull Collection<Placeholder> placeholdersCollection, @NotNull Placeholder... placeholders) {
		message(Permission.empty(), Delay.NONE, translation, placeholdersCollection, placeholders);
	}
	/**
	 * Sends a message to this receiver.
	 * Delays the message by X amount of time before sending it
	 * @param translation translation key
	 * @param delay delay before sending the given message
	 * @param placeholdersCollection placeholders
	 * @param placeholders placeholders
	 */
	default void message(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<Placeholder> placeholdersCollection, @NotNull Placeholder... placeholders){
		message(Permission.empty(), delay, translation, placeholdersCollection, placeholders);
	}
	/**
	 * Sends a message to this receiver.
	 * @param translation translation key
	 * @param placeholders placeholders
	 */
	default void message(@NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		message(Permission.empty(), Delay.NONE, translation, Collections.emptyList(), placeholders);
	}
	/**
	 * Sends a message to this receiver.
	 * Delays the message by X amount of time before sending it
	 * @param translation translation key
	 * @param delay delay before sending the given message
	 * @param placeholders placeholders
	 */
	default void message(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		message(Permission.empty(), delay, translation, Collections.emptyList(), placeholders);
	}
	/**
	 * Sends a message to this receiver.
	 * @param translation translation key
	 * @param placeholdersCollection placeholders
	 */
	default void message(@NotNull TranslationKey translation, @NotNull Collection<Placeholder> placeholdersCollection) {
		message(Permission.empty(), Delay.NONE, translation, placeholdersCollection, new Placeholder[0]);
	}
	/**
	 * Sends a message to this receiver.
	 * Delays the message by X amount of time before sending it
	 * @param translation translation key
	 * @param delay delay before sending the given message
	 * @param placeholdersCollection placeholders
	 */
	default void message(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<Placeholder> placeholdersCollection) {
		message(Permission.empty(), delay, translation, placeholdersCollection, new Placeholder[0]);
	}
}
