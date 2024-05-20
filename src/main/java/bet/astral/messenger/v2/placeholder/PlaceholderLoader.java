package bet.astral.messenger.v2.placeholder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

// TODO Rethink how this should work
public interface PlaceholderLoader {
	List<Map<String, Object>> loadPlaceholders();
	Placeholder loadPlaceholders(@NotNull Map<String, Object> placeholders);
	@NotNull
	Map<String, Placeholder> loadPlaceholders(@NotNull File file, String... path);
	@NotNull
	Map<String, Placeholder> getGlobalPlaceholders();
	void overrideGlobalPlaceholder(@NotNull Placeholder placeholder);
	void overrideGlobalPlaceholders(@NotNull Placeholder... placeholders);
	void overrideGlobalPlaceholders(@NotNull Collection<@NotNull Placeholder> placeholders);
	void overrideAllGlobalPlaceholders(@NotNull Map<@NotNull String, @NotNull Placeholder> placeholders);
}
