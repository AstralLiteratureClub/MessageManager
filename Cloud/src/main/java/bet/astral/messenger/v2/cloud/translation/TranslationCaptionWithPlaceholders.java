package bet.astral.messenger.v2.cloud.translation;

import bet.astral.messenger.v2.cloud.placeholders.VariablePlaceholder;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.PlaceholderList;
import bet.astral.messenger.v2.translation.Translation;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionVariable;
import org.jetbrains.annotations.NotNull;

public interface TranslationCaptionWithPlaceholders extends CaptionTranslationKey{
	static TranslationCaptionWithPlaceholders of(@NotNull TranslationKey translation, PlaceholderList placeholder){
		return new TranslationCaptionWithPlaceholdersImpl(translation.getKey(), placeholder);
	}
	static TranslationCaptionWithPlaceholders of(@NotNull TranslationKey translation, Placeholder... placeholder){
		PlaceholderList placeholders = new PlaceholderList();
		placeholders.addAll(placeholder);
		return new TranslationCaptionWithPlaceholdersImpl(translation.getKey(), placeholders);
	}
	static TranslationCaptionWithPlaceholders of(@NotNull TranslationKey translation, CaptionVariable... placeholder){
		PlaceholderList placeholders = new PlaceholderList();
		placeholders.addAll(VariablePlaceholder.of(placeholder));
		return new TranslationCaptionWithPlaceholdersImpl(translation.getKey(), placeholders);
	}
	static TranslationCaptionWithPlaceholders of(@NotNull Caption translation, PlaceholderList placeholder){
		return new TranslationCaptionWithPlaceholdersImpl(translation.key(), placeholder);
	}
	static TranslationCaptionWithPlaceholders of(@NotNull Caption translation, Placeholder... placeholder){
		PlaceholderList placeholders = new PlaceholderList();
		placeholders.addAll(placeholder);
		return new TranslationCaptionWithPlaceholdersImpl(translation.key(), placeholders);
	}
	static TranslationCaptionWithPlaceholders of(@NotNull Caption translation, CaptionVariable... placeholder){
		PlaceholderList placeholders = new PlaceholderList();
		placeholders.addAll(VariablePlaceholder.of(placeholder));
		return new TranslationCaptionWithPlaceholdersImpl(translation.key(), placeholders);
	}

	PlaceholderList getPlaceholders();
}
