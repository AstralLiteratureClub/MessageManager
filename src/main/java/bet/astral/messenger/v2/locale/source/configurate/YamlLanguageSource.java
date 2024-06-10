package bet.astral.messenger.v2.locale.source.configurate;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.locale.source.FileLanguageSource;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class YamlLanguageSource extends AbstractConfigurateSource implements FileLanguageSource {
	public YamlLanguageSource(@NotNull Messenger messenger, @NotNull Locale locale, @NotNull File file) throws IOException {
		super(messenger, locale, file,
				YamlConfigurationLoader
						.builder()
						.file(file)
						.build()
				);
	}
}
