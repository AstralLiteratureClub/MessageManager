package bet.astral.messenger.v2.cloud;


import bet.astral.messenger.v2.cloud.suggestion.MessengerTooltipSuggestion;
import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.info.MessageInfo;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public interface CloudMessenger extends Messenger {
	/***
	 * Returns a new rich description passed from a component message
	 * @param messageInfo message info
	 * @return chat
	 */
	default @Nullable Description parseDescription(@NotNull MessageInfo messageInfo) {
		Component value = parseComponent(messageInfo, ComponentType.CHAT);
		if (value == null){
			return null;
		}
		return RichDescription.of(value);
	}

	/**
	 * Returns a new rich description parsed from a component message
	 * @param translationKey translation
	 * @param locale locale
	 * @param placeholders placeholders
	 * @return description
	 */
	@Nullable
	default Description parseDescription(@NotNull TranslationKey translationKey, @NotNull Locale locale, @NotNull Placeholder... placeholders) {
		Component value = parseComponent(translationKey, locale, ComponentType.CHAT, placeholders);
		if (value == null){
			return null;
		}
		return RichDescription.of(value);
	}

	/**
	 * Returns a new suggestion based on this messenger
	 * @param suggestion suggestion
	 * @param messageInfo messenger info
	 * @return new messenger tooltip suggestion
	 */
	@NotNull
	default MessengerTooltipSuggestion createSuggestion(@NotNull String suggestion, @NotNull MessageInfo messageInfo){
		return MessengerTooltipSuggestion.of(suggestion, this, messageInfo);
	}

	/**
	 * Returns a new suggestion based on this messenger.
	 * @param suggestion suggestion
	 * @param translationKey translation key
	 * @param locale locale
	 * @param placeholders placeholders
	 * @return new messenger tooltip suggestion
	 */
	@NotNull
	default MessengerTooltipSuggestion createSuggestion(@NotNull String suggestion, @NotNull TranslationKey translationKey, @NotNull Locale locale, Placeholder... placeholders){
		return MessengerTooltipSuggestion.of(suggestion, this, translationKey, locale, placeholders);
	}

}
