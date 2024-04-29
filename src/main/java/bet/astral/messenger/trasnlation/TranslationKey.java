package bet.astral.messenger.trasnlation;

import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public class TranslationKey implements Translatable {
	private final String key;

	public TranslationKey(@NotNull String key) {
		this.key = key;
	}

	@Override
	public @NotNull String translationKey() {
		return key;
	}
}
