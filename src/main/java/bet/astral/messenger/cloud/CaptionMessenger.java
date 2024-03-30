package bet.astral.messenger.cloud;

import bet.astral.messenger.message.message.IMessage;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionFormatter;
import org.incendo.cloud.caption.CaptionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CaptionMessenger<Comp, Sender> extends CaptionProvider<Sender>, CaptionFormatter<Sender, Comp> {
	@Nullable
	CommandManager<Sender> getCommandManager();
	@Nullable
	IMessage<?, Comp> loadMessage(@NotNull Caption caption);
	@Nullable
	IMessage<?, Comp> getMessage(@NotNull Caption caption);

	@Nullable
	IMessage<?, Comp> loadMessage(@NotNull String key);
	@Nullable
	IMessage<?, Comp> getMessage(@NotNull String key);
}
