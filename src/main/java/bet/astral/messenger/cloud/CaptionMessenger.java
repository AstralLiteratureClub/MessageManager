package bet.astral.messenger.cloud;

import bet.astral.messenger.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionFormatter;
import org.incendo.cloud.caption.CaptionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface CaptionMessenger extends CaptionProvider<CommandSender>, CaptionFormatter<CommandSender, Component> {
	@Nullable
	CommandManager<CommandSender> commandManager();
	@Nullable
	Message loadMessage(@NotNull Caption caption);
	@Nullable
	Message getMessage(@NotNull Caption caption);
	/**
	 * Registers the command manager to this messenger.
	 * If the command manager is already registered, an exception will be thrown
	 * @param commandManager command manager
	 * @throws IllegalStateException if the command manager is already registered
	 */
	void registerCommandManager(CommandManager<CommandSender> commandManager) throws IllegalStateException;
}
