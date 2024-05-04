package bet.astral.messenger.translation;

import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public class TranslationKey implements Translatable {
	public static TranslationKey of(String key){
		return new TranslationKey(key);
	}
	private final String key;

	public TranslationKey(@NotNull String key) {
		this.key = key;
	}

	@Override
	public @NotNull String translationKey() {
		return key;
	}
}
