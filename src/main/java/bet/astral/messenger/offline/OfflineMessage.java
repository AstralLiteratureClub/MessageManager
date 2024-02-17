package bet.astral.messenger.offline;

import bet.astral.messenger.Message;
import bet.astral.messenger.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class OfflineMessage extends Message {
	private static String generateKey(){
		return RandomStringUtils.random(7, true, true);
	}
	private String id;
	public OfflineMessage(@NotNull String key, @NotNull Map<Type, Component> messages, @Nullable Map<String, Placeholder> builtInComponents) {
		super(key, messages, builtInComponents);
	}

	public OfflineMessage(@NotNull String key, @NotNull Map<Type, Component> messages) {
		super(key, messages);
	}

	public OfflineMessage(@NotNull String key, @NotNull Component message) {
		super(key, message);
	}
}
