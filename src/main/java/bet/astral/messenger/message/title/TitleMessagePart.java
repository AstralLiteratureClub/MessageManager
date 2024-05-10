package bet.astral.messenger.message.title;

import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.message.MessageType;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

@Getter
public class TitleMessagePart<C> implements IMessagePart<C> {
	@Getter(AccessLevel.NONE)
	private final C component;
	@Getter(AccessLevel.NONE)
	private final String serialized;
	protected final Duration fadeIn;
	protected final Duration stay;
	protected final Duration fadeOut;
	protected final boolean title;

	public TitleMessagePart(C component, String serialized, Duration fadeIn, Duration stay, Duration fadeOut, boolean title) {
		this.component = component;
		this.serialized = serialized;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.title = title;
	}


	public TitleMessagePart(Duration fadeIn, Duration stay, Duration fadeOut, boolean title) {
		this.component = null;
		this.serialized = null;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.title = title;
	}

	@Override
	public @NotNull MessageType getType() {
		return title ? MessageType.TITLE : MessageType.SUBTITLE;
	}

	@Override
	public C asComponent() {
		return component;
	}

	@Override
	public String asString() {
		return serialized;
	}
}
