package bet.astral.messenger.message.adventure.serializer;

import bet.astral.messenger.message.serializer.IMessageTypeSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ComponentTypeSerializer implements IMessageTypeSerializer<Component> {
	private static final MiniMessage mm = MiniMessage.miniMessage();
	@Override
	public @NotNull String serialize(@NotNull Component message) {
		return mm.serialize(message);
	}

	@Override
	public @NotNull Component deserialize(@NotNull String s) {
		return mm.deserialize(s);
	}

	@Override
	public @NotNull Component combine(@NotNull List<Component> messages) {
		Component finalComp = null;
		if (messages.isEmpty()){
			return Component.empty();
		}
		for (Component component : messages){
			if (finalComp != null){
				finalComp = finalComp.append(component);
			} else {
				finalComp = component;
			}
		}
		return finalComp;
	}

	@Override
	public @NotNull Component appendSpace(@NotNull Component component) {
		return component.appendSpace();
	}

	@Override
	public @NotNull Component appendNewLine(@NotNull Component component) {
		return component.appendNewline();
	}
}
