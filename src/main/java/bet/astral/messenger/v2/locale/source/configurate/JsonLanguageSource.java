package bet.astral.messenger.v2.locale.source.configurate;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.locale.source.FileLanguageSource;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class JsonLanguageSource extends AbstractConfigurateSource implements FileLanguageSource {
	public JsonLanguageSource(@NotNull Messenger messenger, @NotNull Locale locale, @NotNull File file) throws IOException {
		super(messenger, locale, file, GsonConfigurationLoader
				.builder()
				.file(file)
				.build()
		);
	}
}
