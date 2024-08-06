package bet.astral.messenger.v2.cloud.exceptions;

import bet.astral.messenger.v2.cloud.placeholders.VariablePlaceholder;
import bet.astral.messenger.v2.cloud.translation.CaptionTranslationKey;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.exception.parsing.ParserException;

public class TranslationException extends ParserException {
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull Caption errorCaption, @NonNull Placeholder... placeholders) {
		super(cause, argumentParser, context, errorCaption, VariablePlaceholder.reference(placeholders));
	}
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull Caption errorCaption, @NonNull CaptionVariable... placeholders) {
		super(cause, argumentParser, context, errorCaption, VariablePlaceholder.of(placeholders));
	}
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull TranslationKey translationKey, @NonNull Placeholder... placeholders) {
		super(cause, argumentParser, context, CaptionTranslationKey.of(translationKey), VariablePlaceholder.reference(placeholders));
	}
	public TranslationException(@Nullable Throwable cause, @NonNull Class<?> argumentParser, @NonNull CommandContext<?> context, @NonNull TranslationKey translationKey, @NonNull CaptionVariable... placeholders) {
		super(cause, argumentParser, context, CaptionTranslationKey.of(translationKey), VariablePlaceholder.of(placeholders));
	}
}
