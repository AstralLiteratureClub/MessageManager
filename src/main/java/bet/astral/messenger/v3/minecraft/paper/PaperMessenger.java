package bet.astral.messenger.v3.minecraft.paper;

import bet.astral.messenger.v2.AbstractMessenger;
import bet.astral.messenger.v2.DefaultScheduler;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.info.MessageInfo;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v3.minecraft.paper.receiver.ConsoleReceiver;
import bet.astral.messenger.v3.minecraft.paper.scheduler.ASyncScheduler;
import de.cubbossa.translations.ComponentSplit;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PaperMessenger extends AbstractMessenger {
	public static Plugin PLUGIN = null;
	public static PlayerManager playerManager;

	public static void init(Plugin plugin){
		if (PLUGIN != null && PLUGIN.isEnabled()){
			return;
		}

		try {
			new PaperPlatform();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			plugin.getComponentLogger().error("Tried to initialize paper platform, but encountered an exception", e);
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}

		DefaultScheduler.ASYNC_SCHEDULER = ASyncScheduler.SCHEDULER;
		PLUGIN = plugin;
		playerManager = new PlayerManager();
		plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
	}

	public PaperMessenger(Logger logger) {
		super(logger);
		registerReceiverConverter(o->{
			if (o instanceof Player player) {
				return playerManager.players.get(player.getUniqueId());
			} else if (o instanceof CommandSender sender){
				if (sender instanceof ConsoleCommandSender consoleCommandSender) {
					return console();
				}
			} else if (o instanceof Receiver receiver){
				return receiver;
			}
			return null;
		});
	}

	public PaperMessenger(Logger logger, Random random) {
		super(logger, random);
	}

	@Override
	public List<Receiver> getPlayers() {
		return new LinkedList<>(playerManager.players.values());
	}

	public PlayerManager getPlayerManager(){
		return playerManager;
	}

	@Override
	public Receiver console() {
		return ConsoleReceiver.CONSOLE_RECEIVER;
	}

	/**
	 * Parses message component using CHAT component type and returns given message split with "\n". Returns split message in {@link ItemLore}
	 * @param translationKey translation key
	 * @return lore, else null
	 */
	@Nullable
	public ItemLore itemLore(TranslationKey translationKey){
		return itemLore(createMessage(translationKey).create());
	}
	/**
	 * Parses message component using CHAT component type and returns given message split with "\n". Returns split message in {@link ItemLore}
	 * @param translationKey translation key
	 * @param locale locale to the parse message with
	 * @return lore, else null
	 */
	@Nullable
	public ItemLore itemLore(TranslationKey translationKey, Locale locale){
		return itemLore(createMessage(translationKey).withLocale(locale).create());
	}
	/**
	 * Parses message component using CHAT component type and returns given message split with "\n". Returns split message in {@link ItemLore}
	 * @param translationKey translation key
	 * @param receiver receiver who the lore is being parsed for
	 * @return lore, else null
	 */
	@Nullable
	public ItemLore itemLore(TranslationKey translationKey, Receiver receiver){
		return itemLore(createMessage(translationKey).withReceiver(receiver).create());
	}
	/**
	 * Parses message component using CHAT component type and returns given message split with "\n". Returns split message in {@link ItemLore}
	 * @param translationKey translation key
	 * @param receiver receiver who the lore is being parsed for. Converts given receiver using {@link #convertReceiver(Object)}
	 * @return lore, else null
	 */
	public ItemLore itemLore(TranslationKey translationKey, Object receiver){
		return itemLore(createMessage(translationKey).withReceiver(receiver).create());
	}
	/**
	 * Parses message component using CHAT component type and returns given message split with "\n". Returns split message in {@link ItemLore}
	 * @param messageInfo message info
	 * @return lore, else null
	 */
	public ItemLore itemLore(MessageInfo messageInfo){
		Component component = messageInfo.parseAsComponent(this, ComponentType.CHAT);
		if (component == null){
			return null;
		}

		List<? extends Component> split = ComponentSplit.split(component, "\n");
		return ItemLore.lore(split);
	}
}
