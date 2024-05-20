package bet.astral.messenger.v2.translation;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class TranslationKeyRegistryImpl implements TranslationKeyRegistry{
	private final Map<String, TranslationKey> translationKeyMap = new HashMap<>();
	@Override
	public @NotNull Set<@NotNull TranslationKey> getTranslationKeys() {
		return new HashSet<>(translationKeyMap.values());
	}

	@Override
	public boolean isRegistered(@NotNull TranslationKey translationKey) {
		return translationKeyMap.get(translationKey.getKey().toLowerCase()) != null;
	}

	@Override
	public boolean isRegistered(@NotNull String key) {
		return translationKeyMap.get(key.toLowerCase()) != null;
	}

	@Override
	public void register(@NotNull TranslationKey translationKey) {
		this.translationKeyMap.put(translationKey.getKey().toLowerCase(), translationKey);
	}

	@Override
	public void unregister(@NotNull TranslationKey translationKey) {
		this.translationKeyMap.remove(translationKey.getKey().toLowerCase());
	}

	@Override
	public void unregister(@NotNull String translationKey) {
		this.translationKeyMap.remove(translationKey.toLowerCase());
	}
}
