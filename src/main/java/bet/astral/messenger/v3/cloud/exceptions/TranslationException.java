package bet.astral.messenger.v3.cloud.exceptions;

import bet.astral.messenger.v3.cloud.placeholders.VariablePlaceholder;
import bet.astral.messenger.v3.cloud.translation.CaptionTranslationKey;
import bet.astral.messenger.v3.cloud.translation.TranslationCaptionWithPlaceholders;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.exception.parsing.ParserException;


public class TranslationException extends ParserException {
	private final TranslationKey translation;
	private final VariablePlaceholder[] placeholders;
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull Caption errorCaption, @NonNull Placeholder... placeholders) {
		super(cause, argumentParser, context, TranslationCaptionWithPlaceholders.of(errorCaption, placeholders), VariablePlaceholder.reference(placeholders));
		this.translation = CaptionTranslationKey.of(errorCaption);
		this.placeholders = VariablePlaceholder.reference(placeholders);
	}
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull Caption errorCaption, @NonNull CaptionVariable... placeholders) {
		super(cause, argumentParser, context, TranslationCaptionWithPlaceholders.of(errorCaption, placeholders), VariablePlaceholder.of(placeholders));
		this.translation = CaptionTranslationKey.of(errorCaption);
		this.placeholders = VariablePlaceholder.of(placeholders);
	}
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull Caption errorCaption) {
		super(cause, argumentParser, context, CaptionTranslationKey.of(errorCaption));
		this.translation = CaptionTranslationKey.of(errorCaption);
		this.placeholders = VariablePlaceholder.reference();
	}
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull TranslationKey translationKey, @NonNull Placeholder... placeholders) {
		super(cause, argumentParser, context, TranslationCaptionWithPlaceholders.of(translationKey, placeholders), VariablePlaceholder.reference(placeholders));
		this.translation = CaptionTranslationKey.of(translationKey);
		this.placeholders = VariablePlaceholder.reference(placeholders);
	}
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull TranslationKey translationKey, @NonNull CaptionVariable... placeholders) {
		super(cause, argumentParser, context, TranslationCaptionWithPlaceholders.of(translationKey, placeholders), VariablePlaceholder.of(placeholders));
		this.translation = CaptionTranslationKey.of(translationKey);
		this.placeholders = VariablePlaceholder.of(placeholders);
	}
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull TranslationKey translationKey) {
		super(cause, argumentParser, context, CaptionTranslationKey.of(translationKey));
		this.translation = CaptionTranslationKey.of(translationKey);
		this.placeholders = VariablePlaceholder.reference();
	}

	public TranslationException(@NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull Caption errorCaption, @NonNull Placeholder... placeholders) {
		super(argumentParser, context, TranslationCaptionWithPlaceholders.of(errorCaption, placeholders), VariablePlaceholder.reference(placeholders));
		this.translation = CaptionTranslationKey.of(errorCaption);
		this.placeholders = VariablePlaceholder.reference(placeholders);
	}
	public TranslationException(@NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull Caption errorCaption, @NonNull CaptionVariable... placeholders) {
		super(argumentParser, context, TranslationCaptionWithPlaceholders.of(errorCaption, placeholders), VariablePlaceholder.of(placeholders));
		this.translation = CaptionTranslationKey.of(errorCaption);
		this.placeholders = VariablePlaceholder.of(placeholders);
	}
	public TranslationException(@NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull Caption errorCaption) {
		super(argumentParser, context, errorCaption);
		this.translation = CaptionTranslationKey.of(errorCaption);
		this.placeholders = VariablePlaceholder.reference();
	}
	public TranslationException(@NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull TranslationKey translationKey, @NonNull Placeholder... placeholders) {
		super(argumentParser, context, TranslationCaptionWithPlaceholders.of(translationKey, placeholders), VariablePlaceholder.reference(placeholders));
		this.translation = CaptionTranslationKey.of(translationKey);
		this.placeholders = VariablePlaceholder.reference(placeholders);
	}
	public TranslationException(@NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull TranslationKey translationKey, @NonNull CaptionVariable... placeholders) {
		super(argumentParser, context, TranslationCaptionWithPlaceholders.of(translationKey, placeholders), VariablePlaceholder.of(placeholders));
		this.translation = CaptionTranslationKey.of(translationKey);
		this.placeholders = VariablePlaceholder.of(placeholders);
	}
	public TranslationException(@NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull TranslationKey translationKey) {
		super(argumentParser, context, CaptionTranslationKey.of(translationKey));
		this.translation = CaptionTranslationKey.of(translationKey);
		this.placeholders = VariablePlaceholder.reference();
	}

	public TranslationKey getTranslation() {
		return translation;
	}

	public VariablePlaceholder[] getPlaceholders() {
		return placeholders;
	}
}
