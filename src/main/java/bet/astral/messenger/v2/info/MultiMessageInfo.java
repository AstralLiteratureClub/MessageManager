package bet.astral.messenger.v2.info;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.annotations.Immutable;
import bet.astral.messenger.v2.placeholder.Placeholder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Map;

/**
 * Represents a message info group. Sends messages in order to receivers
 */
@Immutable
public interface MultiMessageInfo extends Iterable<MessageInfo> {
	/**
	 * Returns the all messages in queue to being sent
	 * @return messages
	 */
	@NotNull
	@Immutable
	ArrayList<MessageInfo> getMessages();

	/**
	 * Returns message for given position from the message list from the message queue
	 * @param position position
	 * @return message info
	 * @throws IndexOutOfBoundsException if index is out of bounds of the array list
	 */
	@NotNull
	MessageInfo getMessage(@Range(from = 0, to = Integer.MAX_VALUE) int position) throws IndexOutOfBoundsException;

	/**
	 * Returns the number of messages in the multi message info
	 * @return size
	 */
	@Range(from = 0, to = Integer.MAX_VALUE)
	int size();

	/**
	 * Returns the placeholders which are shared between all the messages in the queue
	 * @return placeholders
	 */
	@NotNull
	@Immutable
	Map<String, Placeholder> getPlaceholders();

	/**
	 * Sends the multi message info to correct receivers with given messenger
	 * @param messenger messenger
	 */
	default void send(@NotNull Messenger messenger) {
		messenger.send(this);
	}
}
