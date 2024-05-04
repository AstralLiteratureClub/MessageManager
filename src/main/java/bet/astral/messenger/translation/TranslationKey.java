package bet.astral.messenger.translation;

import net.kyori.adventure.translation.Translatable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.minecraft.extras.caption.TranslatableCaption;
import org.jetbrains.annotations.NotNull;

public class TranslationKey implements Translatable, Caption {
	public static TranslationKey of(String key){
		return new TranslationKey(key);
	}

	@Override
	public @NonNull String key() {
		return key;
	}

	private final String key;

	public TranslationKey(@NotNull String key) {
		this.key = key;
	}

	@Override
	public @NotNull String translationKey() {
		return key;
	}

	public @NotNull Caption asTranslatableCaption(){
		return TranslatableCaption.translatableCaption(key);
	}
}

