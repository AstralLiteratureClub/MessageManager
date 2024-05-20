package bet.astral.messenger.v2.locale.source;

import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Language file which uses a file as the source.
 */
public interface FileLanguageSource extends LanguageSource {
	/**
	 * Creates a new file language source which has a yaml parser as the message loader
	 * @param registry the translation key registry used for messenger
	 * @param locale locale for the given language source
	 * @param file source file
	 * @return file language source
	 * @throws IOException if some file exception occurred while getting file setup ready
	 */
	@NotNull
	static FileLanguageSource yaml(@NotNull TranslationKeyRegistry registry, @NotNull Locale locale, @NotNull File file) throws IOException {
		return new YamlLanguageSource(locale, registry, file);
	}

	/**
	 * Creates a new file language source which has a json parser as the message loader
	 * @param registry the translation key registry used for messenger
	 * @param locale locale for the given language source
	 * @param file source file
	 * @return file language source
	 * @throws IOException if some file exception occurred while getting file setup ready
	 */
	@NotNull
	static FileLanguageSource json(@NotNull TranslationKeyRegistry registry, @NotNull Locale locale, @NotNull File file) throws IOException {
		return new JsonLanguageSource(locale, registry, file);
	}
	/**
	 * Returns the file which this language loads messages from
	 * @return file
	 */
	@NotNull
	File getFile();
}
