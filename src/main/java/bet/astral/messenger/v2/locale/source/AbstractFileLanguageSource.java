package bet.astral.messenger.v2.locale.source;

import bet.astral.messenger.v2.Messenger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;

public abstract class AbstractFileLanguageSource extends AbstractLanguageSource implements FileLanguageSource{
	private final File file;

	protected AbstractFileLanguageSource(@NotNull Messenger messenger, @NotNull Locale locale, @NotNull File file) {
		super(messenger, locale);
		this.file = file;
	}

	@Override
	public @NotNull File getFile() {
		return file;
	}
}
