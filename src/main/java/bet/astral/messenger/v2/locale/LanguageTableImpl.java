package bet.astral.messenger.v2.locale;

import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.locale.source.LanguageSource;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageTableImpl implements LanguageTable{
	private final TranslationKeyRegistry registry;
	private final LanguageSource languageSource;
	private final Locale locale;
	private final Map<TranslationKey, ComponentBase> componentBaseMap = new HashMap<>();

	public LanguageTableImpl(TranslationKeyRegistry registry, LanguageSource languageSource, Locale locale) {
		this.registry = registry;
		this.languageSource = languageSource;
		this.locale = locale;
	}

	@Override
	public @NotNull TranslationKeyRegistry getTranslationkeyRegistry() {
		return registry;
	}

	@Override
	public @NotNull Locale getLocale() {
		return locale;
	}

	@Override
	public @NotNull LanguageSource getLanguageSource() {
		return languageSource;
	}

	@Override
	public @NotNull Map<@NotNull TranslationKey, @NotNull ComponentBase> getBaseComponents() {
		return new HashMap<>(componentBaseMap);
	}

	@Override
	public void addComponentBase(@NotNull TranslationKey translationKey, @NotNull ComponentBase componentBase) {
		componentBaseMap.put(translationKey, componentBase);
	}
}
