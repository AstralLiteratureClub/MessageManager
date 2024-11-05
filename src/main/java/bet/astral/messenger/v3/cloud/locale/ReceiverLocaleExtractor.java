package bet.astral.messenger.v3.cloud.locale;

import bet.astral.messenger.v2.receiver.Receiver;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.translations.LocaleExtractor;

import java.util.Locale;

public class ReceiverLocaleExtractor implements LocaleExtractor<Receiver>{
	public static final ReceiverLocaleExtractor INSTANCE = new ReceiverLocaleExtractor();
	@Override
	public @NonNull Locale extract(@NonNull Receiver recipient) {
		return recipient.getLocale();
	}
}
