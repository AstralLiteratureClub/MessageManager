package bet.astral.messenger.v2.cloud.translation;

import bet.astral.messenger.v2.translation.TranslationKey;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.minecraft.extras.caption.TranslatableCaption;
import org.jetbrains.annotations.NotNull;

/**
 * Returns a caption translation key which can be used in Messenger and Cloud at the same time.
 */
public interface CaptionTranslationKey extends TranslationKey, Caption {
	/**
	 * Returns a new instance of the caption translation key.
	 * @param key key
	 * @return new translation key
	 */
	static CaptionTranslationKey of(@NotNull String key){
		return new CaptionTranslationImpl(key);
	}
	/**
	 * Returns a new instance of the caption translation key.
	 * @param caption caption
	 * @return new translation key
	 */
	static CaptionTranslationKey of(@NotNull Caption caption){
		return new CaptionTranslationImpl(caption.key());
	}
	/**
	 * Returns a new instance of the caption translation key.
	 * @param translationKey translation key
	 * @return new translation key
	 */
	static CaptionTranslationKey of(@NotNull TranslationKey translationKey){
		return new CaptionTranslationImpl(translationKey.getKey());
	}
	/**
	 * Converts this translation key to a {@link TranslatableCaption} which is sent by cloud as translatable.
	 * @return translatable caption
	 */
	@NotNull
	default Caption toTranslatable() {
		return TranslatableCaption.translatableCaption(this.key());
	}

	@Override
	default @NonNull String key() {
		return getKey();
	}

	@Override
	int hashCode();
}
