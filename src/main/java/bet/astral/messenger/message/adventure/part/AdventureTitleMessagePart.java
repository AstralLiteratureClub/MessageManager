package bet.astral.messenger.message.adventure.part;

import bet.astral.messenger.message.title.TitleMessagePart;
import com.mojang.brigadier.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

// TODO implement
@SuppressWarnings("NullableProblems")
public class AdventureTitleMessagePart extends TitleMessagePart<Component> implements ComponentLike, Message {
	private final static MiniMessage minimessage = MiniMessage.miniMessage();

	public AdventureTitleMessagePart(@NotNull Component component, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut, boolean title) {
		super(component, minimessage.serialize(component), fadeIn, stay, fadeOut, title);
	}

	public Title asTitle() {
		return Title.title(
				title ? Objects.requireNonNull(asComponent()) : Component.empty(),
				!title ? Objects.requireNonNull(asComponent()) : Component.empty(),
				asTimes());
	}

	public Title.Times asTimes(){
		return Title.Times.times(fadeIn, stay, fadeOut);
	}

	@Override
	public String getString() {
		Component component = asComponent();
		if (component == null){
			return "";
		}
		return PlainTextComponentSerializer.plainText().serialize(component);
	}
}
