package bet.astral.messenger.cloud;

import bet.astral.messenger.Message;
import bet.astral.messenger.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class PlainMessage extends Message {
	private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();
	public PlainMessage(@NotNull String key, @NotNull Map<Type, Component> messages, @Nullable Map<String, Placeholder> builtInComponents) {
		super(key, messages, builtInComponents);
	}

	public PlainMessage(@NotNull String key, @NotNull Map<Type, Component> messages) {
		super(key, messages);
	}

	public PlainMessage(@NotNull String key, @NotNull Component message) {
		super(key, message);
	}

	@Override
	public @Nullable String stringValue(Type type) {
		return super.stringValue(type);
	}

	@Override
	public @Nullable Component componentValue(Type type) {
		if (components().get(type) == null){
			return null;
		}
		return PLAIN.deserialize(PLAIN.serialize(Objects.requireNonNull(super.componentValue(type))));
	}
}
