package bet.astral.messenger.v2.translation;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

record TranslationKeyImpl(@NotNull String translationKey) implements TranslationKey {

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		TranslationKeyImpl that = (TranslationKeyImpl) object;
		return Objects.equals(translationKey, that.translationKey);
	}

	@Override
	public int hashCode() {
		return 23*Objects.hash(translationKey);
	}

	@Override
	public @NotNull String getKey() {
		return translationKey;
	}
}
