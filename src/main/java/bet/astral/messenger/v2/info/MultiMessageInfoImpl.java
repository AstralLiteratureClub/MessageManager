package bet.astral.messenger.v2.info;

import bet.astral.messenger.v2.annotations.Immutable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.*;

@Immutable
class MultiMessageInfoImpl implements MultiMessageInfo {
	private final ArrayList<MessageInfo> messages;

	public MultiMessageInfoImpl(ArrayList<MessageInfo> messages) {
		this.messages = messages;
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

	@NotNull
	@Override
	public Iterator<MessageInfo> iterator() {
		return messages.iterator();
	}
}
