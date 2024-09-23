# Messenger
Messenger is a multi-language supporting messaging API to talk to players from server. Messenger can be used client side only too, but all of the parsing of the messages happens in the server, not client. (AKA implement server features in client to work it in client)

# How to use it

```java
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.locale.LanguageTable;
import bet.astral.messenger.v2.locale.source.LanguageSource;
import bet.astral.messenger.v2.locale.source.FileLanguageSource;
import java.io.File;
import java.util.*;

public class Init {

	private Messenger messenger;
	private File dataFolder = getDataFolder();
	private TranslationKey ONE_MESSAGE = TranslationKey.of("hello-key");
	private TranslationKey TWO_MESSAGE = TranslationKey.of("bye-key");

	public void init() {
	Locale defaultLocale = Locale.US;
	org.slf4j.Logger logger = getLogger();

	messenger = Messenger.of(logger,
			new Random(System.currentTimeMillis()),
			defaultLocale);

	Locale[] locales = new Locale[]{Locale.US, Locale.GERMAN};
	for (Locale locale : locales) {
		String localeName = locale.getLanguage() + "_" + locale.getCountry();
		File file = new File(dataFolder, localeName + ".json");
		if (!file.exists()) {
			file.createNewFile();
			// Import the data to the file blah blah blah
		}
		LanguageSource source = FileLanguageSource.gson(messenger, locale, file, MiniMessage.miniMessage());
		LanguageTable table = LanguageTable.of(source);
		messenger.registerLanguageTable(table);
		messenger.loadTranslations(locale, ONE_MESSAGE, TWO_MESSAGE);
	}

	Receiver receiver = messenger.console();
	
	messenger.message(receiver, ONE_MESSAGE);
	messenger.message(receiver, TWO_MESSAGE);
	}
}
```
