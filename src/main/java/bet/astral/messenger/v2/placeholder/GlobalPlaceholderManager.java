package bet.astral.messenger.v2.placeholder;

import bet.astral.messenger.v2.locale.source.LanguageSource;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public interface GlobalPlaceholderManager {
	@NotNull
	Map<Locale, LanguageSource> getLanguageSources();
	@NotNull
	Map<String, Placeholder> getGlobalPlaceholders();
	void overrideGlobalPlaceholder(@NotNull Placeholder placeholder);
	void overrideGlobalPlaceholders(@NotNull Placeholder... placeholders);
	void overrideGlobalPlaceholders(@NotNull Collection<@NotNull Placeholder> placeholders);
	void overrideAllGlobalPlaceholders(@NotNull Map<@NotNull String, @NotNull Placeholder> placeholders);
}
