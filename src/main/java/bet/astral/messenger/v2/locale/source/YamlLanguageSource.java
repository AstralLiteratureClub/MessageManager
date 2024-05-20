package bet.astral.messenger.v2.locale.source;

import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

class YamlLanguageSource extends AbstractFileLanguageSource implements FileLanguageSource{
	protected YamlLanguageSource(Locale locale, TranslationKeyRegistry registry, File file) throws IOException {
		super(locale, registry, file);
	}

	@Override
	public @Nullable CompletableFuture<@Nullable ComponentBase> load(@NotNull TranslationKey translationKey) {
		return null;
	}

	@Override
	public @Nullable CompletableFuture<@NotNull Map<@NotNull TranslationKey, @Nullable ComponentBase>> loadAll(@NotNull TranslationKey... translationKeys) {
		return null;
	}

	@Override
	public @Nullable CompletableFuture<@NotNull Map<@NotNull TranslationKey, @Nullable ComponentBase>> loadAll(@NotNull Collection<? extends TranslationKey> translationKeys) {
		return null;
	}
}
