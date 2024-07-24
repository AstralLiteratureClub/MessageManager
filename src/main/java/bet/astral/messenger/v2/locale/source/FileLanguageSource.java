package bet.astral.messenger.v2.locale.source;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.locale.source.gson.GsonLanguageSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Language file which uses a file as the source.
 */
public interface FileLanguageSource extends LanguageSource {
	/**
	 * Creates a new file language source which has a gson parser as the message loader
	 * @param messenger the messenger which uses the given language source
	 * @param locale locale for the given language source
	 * @param file source file
	 * @return file language source
	 * @throws IOException if some file exception occurred while getting file setup ready
	 */
	@NotNull
	static FileLanguageSource gson(@NotNull Messenger messenger, @NotNull Locale locale, @NotNull File file, @NotNull ComponentSerializer<Component, Component, String> componentSerializer) throws IOException {
		return new GsonLanguageSource(messenger, locale, file, componentSerializer);
	}

	/**
	 * Returns the file which this language loads messages from
	 * @return file
	 */
	@NotNull
	File getFile();
}
