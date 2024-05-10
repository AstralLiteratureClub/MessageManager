package bet.astral.messenger.message.part;

import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.MessageType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public interface IRandomMessagePart<C> extends IMessage<RandomMessagePart<C>, C> {
	@NotNull
	Random random = new Random((long) (System.currentTimeMillis()*12.42));

	@Nullable
	C generateRandomMessage(@NotNull MessageType type);
	@Nullable
	List<C> generateRandomMessages(@NotNull MessageType type, int amount);
}
