package bet.astral.messenger.cloud;

import bet.astral.messenger.message.message.IMessage;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.caption.Caption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractCommandMessenger<Comp, Sender> implements CaptionMessenger<Comp, Sender> {
	private final CommandManager<Sender> commandManager;

	protected AbstractCommandMessenger(CommandManager<Sender> commandManager) {
		this.commandManager = commandManager;
	}

	@Override
	public @NotNull CommandManager<Sender> getCommandManager() {
		return commandManager;
	}

	@Override
	public @Nullable IMessage<?, Comp> loadMessage(@NotNull Caption caption) {
		return loadMessage(caption.key());
	}

	@Override
	public @Nullable IMessage<?, Comp> getMessage(@NotNull Caption caption) {
		return getMessage(caption.key());
	}
}