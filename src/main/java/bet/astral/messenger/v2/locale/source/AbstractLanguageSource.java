package bet.astral.messenger.v2.locale.source;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public abstract class AbstractLanguageSource implements LanguageSource{
	private final Messenger messenger;
	private final Locale locale;
	private final TranslationKeyRegistry registry;

	protected AbstractLanguageSource(@NotNull Messenger messenger, @NotNull Locale locale) {
		this.messenger = messenger;
		this.locale = locale;
		this.registry = messenger.getTranslationKeyRegistry();
	}

	@Override
	public @NotNull TranslationKeyRegistry getTranslationKeyRegistry() {
		return registry;
	}

	@Override
	public @NotNull Locale getLocale() {
		return locale;
	}

	@Override
	public @NotNull Messenger getMessenger() {
		return messenger;
	}
}
