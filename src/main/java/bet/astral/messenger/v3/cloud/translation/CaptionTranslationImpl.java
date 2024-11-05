package bet.astral.messenger.v3.cloud.translation;

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
		if (!(object instanceof TranslationKey that)) return false;
		return Objects.equals(getKey(), that.getKey());
	}

	@Override
	public int hashCode() {
		return 23*Objects.hash(getKey());
	}

	@Override
	public @NotNull String getKey() {
		return key;
	}
}
