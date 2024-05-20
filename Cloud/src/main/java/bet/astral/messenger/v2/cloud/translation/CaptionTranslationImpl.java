package bet.astral.messenger.v2.cloud.translation;

import org.jetbrains.annotations.NotNull;

class CaptionTranslationImpl implements CaptionTranslationKey{
	private final String key;

	public CaptionTranslationImpl(String key) {
		this.key = key;
	}

	@Override
	public @NotNull String getKey() {
		return key;
	}
}
