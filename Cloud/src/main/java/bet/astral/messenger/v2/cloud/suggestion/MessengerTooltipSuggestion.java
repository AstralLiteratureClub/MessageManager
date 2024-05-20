package bet.astral.messenger.v2.cloud.suggestion;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.info.MessageInfo;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.incendo.cloud.minecraft.extras.suggestion.ComponentTooltipSuggestion;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Creates a new component tooltip suggestion
 * which the player receives and parses the tooltip when sending it to the player.
 */
public interface MessengerTooltipSuggestion extends ComponentTooltipSuggestion {
	/**
	 * Returns a new getMessenger tooltip.
	 * @param suggestion suggestion
	 * @param messenger getMessenger
	 * @param messageInfo message info
	 * @return new getMessenger tooltip suggestion
	 */
	static MessengerTooltipSuggestion of(String suggestion, Messenger messenger, MessageInfo messageInfo) {
		return new MessengerTooltipSuggestionImpl(messenger, messageInfo, suggestion);
	}

	/**
	 * Returns a new getMessenger tooltip.
	 * @param suggestion suggestion
	 * @param messenger getMessenger
	 * @param translationKey translation key
	 * @param locale locale
	 * @param placeholders placeholders
	 * @return new getMessenger tooltip suggestion
	 */
	static MessengerTooltipSuggestion of(@NotNull String suggestion, @NotNull Messenger messenger, @NotNull TranslationKey translationKey, @NotNull Locale locale, Placeholder... placeholders){
		MessageInfo info = messenger
				.createMessage(translationKey)
				.withLocale(locale)
				.useReceiverDelay(false)
				.addPlaceholders(placeholders)
				.create();
		return of(suggestion, messenger, info);
	}

	/**
	 * Returns the getMessenger
	 * @return getMessenger
	 */
	@NotNull
	Messenger getMessenger();

	/**
	 * Returns the message info provided
	 * @return message info
	 */
	@NotNull
	MessageInfo getMessageInfo();
}
