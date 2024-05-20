package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.translation.TranslationKey;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface ComponentLoader {
	@NotNull
	ComponentBase createBase(TranslationKey translationKey);

	/**
	 * Returns the locale which this component loader returns.
	 * @return locale
	 */
	@NotNull Locale getLocale();

	/**
	 * Returns the file type of this component loader.
	 * @return file type
	 */
	@NotNull
	FileType getFileType();
	enum FileType {
		JSON,
		YAML,
	}
}
