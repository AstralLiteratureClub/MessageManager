package bet.astral.messenger.v2.placeholder.loader;

import bet.astral.messenger.v2.placeholder.Placeholder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PlaceholderSource {
	@NotNull
	CompletableFuture<Placeholder> loadPlaceholder(@NotNull String key);
	@NotNull
	CompletableFuture<@NotNull Map<@NotNull String, @NotNull Placeholder>> loadAllPlaceholders(@NotNull String key);
}
