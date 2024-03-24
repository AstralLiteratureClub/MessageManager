package bet.astral.messenger.message.message;

import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.placeholder.Placeholder;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class AbstractMessage<C> implements IMessage<IMessagePart<C>, C>{
	private final Map<String, Placeholder> placeholders;
	private final String key;
	private boolean enabled = true;


	protected AbstractMessage(String key, Map<String, Placeholder> placeholders) {
		this.placeholders = ImmutableMap.copyOf(placeholders);
		this.key = key;
	}

	@Override
	public @NotNull Map<String, Placeholder> placeholders() {
		return placeholders;
	}

	@Override
	public @NotNull String key() {
		return key;
	}

	@Override
	public boolean enabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean v) {
		this.enabled = v;
	}
}
