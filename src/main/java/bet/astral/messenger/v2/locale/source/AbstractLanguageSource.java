package bet.astral.messenger.v2.locale.source;

import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public abstract class AbstractLanguageSource implements LanguageSource{
	private final Locale locale;
	private final TranslationKeyRegistry registry;

	protected AbstractLanguageSource(Locale locale, TranslationKeyRegistry registry) {
		this.locale = locale;
		this.registry = registry;
	}

	@Override
	public @NotNull TranslationKeyRegistry getTranslationKeyRegistry() {
		return registry;
	}

	@Override
	public @NotNull Locale getLocale() {
		return locale;
	}
}
