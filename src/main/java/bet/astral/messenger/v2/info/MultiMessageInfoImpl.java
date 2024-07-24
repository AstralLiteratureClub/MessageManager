package bet.astral.messenger.v2.info;

import bet.astral.messenger.v2.annotations.Immutable;
import bet.astral.messenger.v2.placeholder.Placeholder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.*;

@Immutable
class MultiMessageInfoImpl implements MultiMessageInfo {
	private final ArrayList<MessageInfo> messages;
	private final Map<String, Placeholder> placeholders;

	public MultiMessageInfoImpl(ArrayList<MessageInfo> messages, Map<String, Placeholder> placeholders) {
		this.messages = messages;
		this.placeholders = placeholders;
	}

	@Override
	public @NotNull ArrayList<MessageInfo> getMessages() {
		return new ArrayList<>(messages);
	}

	@Override
	public @NotNull MessageInfo getMessage(@Range(from = 0, to = 10) int id) throws IndexOutOfBoundsException{
		return messages.get(id);
	}

	@Override
	public int size() {
		return messages.size();
	}

	@Override
	public @NotNull Map<String, Placeholder> getPlaceholders() {
		return placeholders;
	}

	@NotNull
	@Override
	public Iterator<MessageInfo> iterator() {
		return messages.iterator();
	}
}
