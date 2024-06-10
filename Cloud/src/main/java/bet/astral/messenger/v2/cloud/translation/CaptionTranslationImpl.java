package bet.astral.messenger.v2.cloud.translation;

import bet.astral.messenger.v2.translation.TranslationKey;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class CaptionTranslationImpl implements CaptionTranslationKey{
	private final String key;

	public CaptionTranslationImpl(String key) {
		this.key = key;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		TranslationKey that = (TranslationKey) object;
		return Objects.equals(translationKey(), that.translationKey());
	}

	@Override
	public int hashCode() {
		return 24*Objects.hash(key);
	}


	@Override
	public @NotNull String getKey() {
		return key;
	}
}
