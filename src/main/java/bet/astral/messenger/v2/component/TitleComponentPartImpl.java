package bet.astral.messenger.v2.component;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class TitleComponentPartImpl extends ComponentPartImpl implements TitleComponentPart{
	private final Duration in;
	private final Duration stay;
	private final Duration out;

	public TitleComponentPartImpl(Component component, Duration in, Duration stay, Duration out) {
		super(component);
		this.in = in;
		this.stay = stay;
		this.out = out;
	}

	@Override
	public @NotNull Duration getFadeIn() {
		return in;
	}

	@Override
	public @NotNull Duration getStay() {
		return stay;
	}

	@Override
	public @NotNull Duration getFadeOut() {
		return out;
	}
}
