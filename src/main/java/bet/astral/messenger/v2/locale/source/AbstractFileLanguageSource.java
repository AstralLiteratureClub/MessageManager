package bet.astral.messenger.v2.locale.source;

import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;

public abstract class AbstractFileLanguageSource extends AbstractLanguageSource implements FileLanguageSource{
	private final File file;

	protected AbstractFileLanguageSource(Locale locale, TranslationKeyRegistry registry, File file) {
		super(locale, registry);
		this.file = file;
	}

	@Override
	public @NotNull File getFile() {
		return file;
	}
}
