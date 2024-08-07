package bet.astral.messenger.v2.translation;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

record TranslationKeyImpl(@NotNull String translationKey) implements TranslationKey {

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof TranslationKey that)) return false;
		return Objects.equals(getKey(), that.getKey());
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
