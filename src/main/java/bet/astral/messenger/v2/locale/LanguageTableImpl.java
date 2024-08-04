package bet.astral.messenger.v2.locale;

import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.locale.source.LanguageSource;
import bet.astral.messenger.v2.placeholder.manager.PlaceholderManager;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageTableImpl implements LanguageTable{
	private final TranslationKeyRegistry registry;
	private final LanguageSource languageSource;
	private final Locale locale;
	private LanguageTable fallback;
	private final PlaceholderManager placeholderManager = PlaceholderManager.create();
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
	public @NotNull Locale getFallbackLocale() {
		return fallback.getLocale();
	}

	@Override
	@Nullable
	public LanguageTable getFallbackTable(){
		return fallback;
	}

	@Override
	public void setFallbackLocale(@NotNull LanguageTable table) {
		this.fallback = table;
	}

	@Override
	public @NotNull LanguageSource getLanguageSource() {
		return languageSource;
	}

	@Override
	public boolean exists(@NotNull TranslationKey translationKey) {
		return componentBaseMap.get(translationKey) != null;
	}

	@Override
	public boolean existsFallback(@NotNull TranslationKey translationKey) {
		if (exists(translationKey)) {
			return true;
		}
		LanguageTable table = getFallbackTable();
		if (table==null){
			return false;
		}
		return table.existsFallback(translationKey);
	}

	@Override
	public @Nullable ComponentBase getComponent(@NotNull TranslationKey key) {
		return componentBaseMap.get(key);
	}

	@Override
	public @Nullable ComponentBase getComponentFallBack(@NotNull TranslationKey translationKey) {
		if (exists(translationKey)) {
			return getComponent(translationKey);
		}
		if (!existsFallback(translationKey)){
			return null;
		}
		if (getFallbackTable()==null){
			return null;
		}
		return getFallbackTable().getComponentFallBack(translationKey);
	}

	@Override
	public @NotNull Map<@NotNull TranslationKey, @NotNull ComponentBase> getBaseComponents() {
		return new HashMap<>(componentBaseMap);
	}

	@Override
	public void addComponentBase(@NotNull TranslationKey translationKey, @NotNull ComponentBase componentBase) {
		componentBaseMap.put(translationKey, componentBase);
	}

	@Override
	public @NotNull PlaceholderManager getPlaceholderManager() {
		return placeholderManager;
	}
}
