package bet.astral.messagemanager.offline;

import bet.astral.messagemanager.Message;
import bet.astral.messagemanager.MessageManager;
import bet.astral.messagemanager.database.MessageDatabase;
import bet.astral.messagemanager.database.PlayerDatabase;
import bet.astral.messagemanager.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class OfflineMessageManager<P extends JavaPlugin> extends MessageManager<P> {
	protected MessageDatabase<P> messageDatabase;
	protected PlayerDatabase<P> playerDatabase;
	protected boolean messagesExpire = true;
	protected long timeToExpire = 604800000; // 7 weeks to expire

	public OfflineMessageManager(P plugin, FileConfiguration config, Map<String, Message> map, MessageDatabase<P> messageDatabase) {
		super(plugin, config, map);
	}

	public OfflineMessageManager(P plugin, FileConfiguration config, Map<String, Message> messageMap, String mainPlaceholders, MessageDatabase<P> messageDatabase) {
		super(plugin, config, messageMap, mainPlaceholders);
	}
	public abstract void setMessageDatabase();

	public MessageDatabase<P> messageDatabase() {
		return this.messageDatabase;
	}

	public PlayerDatabase<P> playerDatabase(){
		return playerDatabase;
	}

	protected OfflineMessage convert(Message message){
		return new OfflineMessage(message.key(), message.components());
	}

	public void messageOffline(OfflinePlayer to, String messageKey, List<Placeholder> placeholders){
		messageOffline(to, messageKey, false, placeholders);
	}
	public void messageOffline(OfflinePlayer to, String messageKey, Placeholder... placeholders){
		messageOffline(to, messageKey, false, List.of(placeholders));
	}

	public void messageOffline(OfflinePlayer to, String  messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messageOffline(to, messageKey, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void messageOffline(@NotNull OfflinePlayer to, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		if (!this.disabledMessages.contains(messageKey)) {
			Message message = this.messagesMap.get(messageKey);
			if (message == null) {
				message = loadMessage(messageKey);
				if (message == null) {
					return;
				}
			}
			OfflineMessage offlineMessage = convert(message);
			List<Placeholder> placeholderList = new LinkedList<>(placeholders);
			if (senderSpecificPlaceholders){
				placeholderList.addAll(createPlaceholders("player", to));
			}
			Component chat_parsed = parse(message, Message.Type.CHAT, placeholderList);
			Component action_bar_parsed = parse(message, Message.Type.ACTION_BAR, placeholderList);
			Component title_parsed = parse(message, Message.Type.TITLE, placeholderList);
			Component subtitle_parsed = parse(message, Message.Type.SUBTITLE, placeholderList);
			ParsedOfflineMessage parsedOfflineMessage = new ParsedOfflineMessage(messageKey, chat_parsed, action_bar_parsed, title_parsed, subtitle_parsed, System.currentTimeMillis()+timeToExpire, messagesExpire);

			messageDatabase.save(parsedOfflineMessage);
		}
	}

}
