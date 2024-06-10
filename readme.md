# Messenger
Messenger is a developer API which allows easier translations for messages.
The Point of messenger is
to make developing plugins easier and fun without needing to write a completely new messaging platform.
Messenger is not intended to be used by server administrators, but the developers.

# How to use it

```java
import javax.sound.midi.Receiver;
import java.io.File;
import java.util.*;

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
		File file = new File(dataFolder, localeName + ".yaml");
		if (!file.exists()) {
			file.createNewFile();
			// Import the data to the file blah blah blah
		}
		LanguageSource source = FileLanguageSource.yaml(messenger, locale, file);
		LanguageTable table = LanguageTable.of(source);
		messenger.registerLanguageTable(table);
	}

	messenger.loadTranslations(ONE_MESSAGE, TWO_MESSAGE);

	Receiver receiver = messenger.console();
	
	messenger.message(receiver, ONE_MESSAGE);
	messenger.message(receiver, TWO_MESSAGE);
}
```