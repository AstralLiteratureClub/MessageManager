package bet.astral.messenger.cloud;

import bet.astral.messenger.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface CaptionMessenger extends CaptionProvider<CommandSender> {
	Map<String, PlainMessage> captionMessages();
	CommandManager<CommandSender> commandManager();
	@NotNull
	PlainMessage loadMessage(Caption caption);
	PlainMessage getMessage(Caption caption);
	String parse(CommandSender sender, Caption caption, List<Placeholder> placeholders);
	default String parse(CommandSender sender, Caption caption, Placeholder... placeholders) {
		return parse(sender, caption, List.of(placeholders));
	}
	default Component parseAsComponent(CommandSender sender, Caption caption, Placeholder... placeholders) {
		return parseAsComponent(sender, caption, List.of(placeholders));
	}
	Component parseAsComponent(CommandSender sender, Caption caption, List<Placeholder> placeholders);

	/**
	 * Registers the command manager to this messenger.
	 * If the command manager is already registered, an exception will be thrown
	 * @param commandManager command manager
	 * @throws IllegalStateException if the command manager is already registered
	 */
	void registerCommandManager(CommandManager<CommandSender> commandManager) throws IllegalStateException;
}
