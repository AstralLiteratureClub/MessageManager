package bet.astral.messenger.message.serializer;

import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.part.IMessagePart;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

public interface IMessageTitleSerializer<M extends IMessage<IMessagePart<C>, C>, C> extends IMessageSerializer<M, IMessagePart<C>, C> {
	@Override
	@ApiStatus.Obsolete
	default @NotNull IMessagePart<C> deserialize(@NotNull String serialized, @NotNull MessageType type) {
		return deserialize(serialized, type, asDuration(10), asDuration(70), asDuration(20));
	}

	default Duration asDuration(long ticks) {
		return Duration.ofMillis(ticks*50);
	}

	@Override
	default @NotNull IMessagePart<C> create(@NotNull MessageType type, @NotNull C value) {
		return create(type, value, asDuration(10), asDuration(70), asDuration(20));
	}

	@NotNull
	IMessagePart<C> create(@NotNull MessageType type, @NotNull C component, @NotNull Duration in, @NotNull Duration stay, @NotNull Duration out);

	@NotNull
	IMessagePart<C> deserialize(@NotNull String deserialized, @NotNull MessageType type, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut);
	@NotNull
	default IMessagePart<C> load(@NotNull Object possibleConfiguration, MessageType type) {
		if (possibleConfiguration instanceof ConfigurationSection section){
			String serialized = section.getString("value");
			if (serialized == null){
				throw new IllegalStateException("Invalid configuration for title! Missing \"value\" field!");
			}
			int in = section.getInt("fade-in", 10);
			int stay = section.getInt("stay", 70);
			int out = section.getInt("fade-out", 20);
			return deserialize(serialized, type, asDuration(in), asDuration(stay), asDuration(out));
		} else if (possibleConfiguration instanceof String s){
			return deserialize(s, type);
		} else if (possibleConfiguration instanceof List<?> list){
			//noinspection unchecked
			return deserialize((List<String>) list, type);
		}
		throw new IllegalArgumentException("Couldn't title deserialize object from configuration: "+ possibleConfiguration.getClass());
	}

	@NotNull C asComponent(@NotNull String serialized);
}
