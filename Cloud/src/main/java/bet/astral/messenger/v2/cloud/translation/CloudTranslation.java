package bet.astral.messenger.v2.cloud.translation;

import bet.astral.messenger.v2.translation.TranslationKey;

import java.util.Objects;

public class CloudTranslation extends bet.astral.messenger.v2.translation.Translation implements CaptionTranslationKey {
	public CloudTranslation(String key) {
		super(key);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof TranslationKey that)) return false;
		return Objects.equals(getKey(), that.getKey());
	}

	@Override
	public int hashCode() {
		return 23 * Objects.hash(getKey());
	}
}
