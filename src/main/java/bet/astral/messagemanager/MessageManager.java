package bet.astral.messagemanager;

import bet.astral.messagemanager.permission.Permission;
import bet.astral.messagemanager.placeholder.LegacyPlaceholder;
import bet.astral.messagemanager.placeholder.MessagePlaceholder;
import bet.astral.messagemanager.placeholder.Placeholder;
import bet.astral.messagemanager.utils.PlaceholderUtils;
import com.google.common.collect.ImmutableMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OfflineMessage manager which loads messages in runtime and parses them.
 * @param <P> Plugin
 */
public class MessageManager<P extends JavaPlugin> {
	protected Pattern placeholder_pattern = Pattern.compile("%([^%]+)%");
	protected final MiniMessage miniMessage = MiniMessage.miniMessage();
	protected final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();
	private final P plugin;
	protected final FileConfiguration config;
	private ImmutableMap<String, Placeholder> immutablePlaceholders;
	protected final Map<String, Message> messagesMap;
	protected final List<String> disabledMessages;
	public boolean useConsoleComponentLogger = false;

	public MessageManager(P plugin, FileConfiguration config, Map<String, Message> map) {
		this(plugin, config, map, "placeholders");
	}
	public MessageManager(P plugin, FileConfiguration config, Map<String, Message> messageMap, String mainPlaceholders) {
		this.plugin = plugin;
		this.config = config;
		this.immutablePlaceholders = this.loadPlaceholders(mainPlaceholders);
		this.messagesMap = messageMap;
		this.disabledMessages = new LinkedList<>();
	}

	public final P plugin(){
		return plugin;
	}

	protected ImmutableMap<String, Placeholder> loadPlaceholders(String key) {
		Map<String, Placeholder> placeholderMap = new HashMap<>();
		List<Map<?, ?>> placeholderMapList = this.config.getMapList(key);

		for (Map<?, ?> map : placeholderMapList){
			Placeholder placeholder;
			String name = (String)map.get("name");
			String value = (String)map.get("value");
			String type = (String)map.get("type");
			if (type == null) {
				type = "default";
			}

			switch (type) {
				case "default":
				case "mini-message":
				case "minimessage":
					Component componentValue = this.miniMessage.deserialize(value);
					placeholder = new Placeholder(name, componentValue);
					break;
				case "legacy":
				case "bukkit":
					placeholder = new LegacyPlaceholder(name, value);
					break;
				case "message":
					Message message = (Message)this.messagesMap.get(value);
					String messagePart = (String)map.get("message-part");
					if (messagePart == null) {
						messagePart = "chat";
					}

					messagePart = messagePart.replaceAll("-", "_");
					messagePart = messagePart.replaceAll(" ", "_");
					Message.Type messageType = Message.Type.valueOf(messagePart);
					if (message == null) {
						placeholder = MessagePlaceholder.emptyPlaceholder(name);
					} else {
						placeholder = MessagePlaceholder.create(name, message, messageType);
					}
					break;
				default:
					throw new RuntimeException("Unknown placeholder serializer type: " + type);
			}
			placeholderMap.put(placeholder.key(), placeholder);
		}

		return ImmutableMap.copyOf(placeholderMap);
	}

	/**
	 * Returns the global placeholders
	 * @return global placeholder
	 */
	@NotNull
	protected Map<String, Placeholder> defaultPlaceholders(){
		return immutablePlaceholders;
	}

	/**
	 * Overrides the global placeholders and returns the old global placeholders
	 * @param newPlaceholders new placeholders
	 * @return old placeholders
	 */
	protected Map<String, Placeholder> overridePlaceholders(Map<String, Placeholder> newPlaceholders){
		Map<String, Placeholder> placeholderMap = immutablePlaceholders;
		this.immutablePlaceholders = ImmutableMap.copyOf(newPlaceholders);
		return placeholderMap;
	}

	@Nullable
	public Message loadMessage(String messageKey) {
		Object messageSection = this.config.get(messageKey);
		Message message;
		if (messageSection instanceof String) {
			Component messageComponent = this.miniMessage.deserialize((String)messageSection);
			message = new Message(messageKey, messageComponent);
			this.messagesMap.put(messageKey, message);
			return message;
		} else {
			Object chatObj;
			if (this.config.getList(messageKey) != null) {
				List<String> list = this.config.getStringList(messageKey);
				Component component = null;

				for(Iterator<String> val = list.iterator(); val.hasNext(); component = component.append(this.miniMessage.deserialize((String)chatObj))) {
					chatObj = val.next();
					if (component != null) {
						component = component.appendNewline();
					} else {
						component = Component.text().build();
					}
				}

				if (component == null) {
					this.disabledMessages.add(messageKey);
					return null;
				} else {
					message = new Message(messageKey, component);
					this.messagesMap.put(messageKey, message);
					return message;
				}
			} else if (!(messageSection instanceof MemorySection memorySection)) {
				throw new IllegalStateException("OfflineMessage configuration for " + messageKey + " is illegal. Please fix it!");
			} else {
				boolean disabled = memorySection.getBoolean("disabled", false);
				if (disabled) {
					this.disabledMessages.add(messageKey);
					return null;
				} else {
					Object componentSerializer = switch (memorySection.getString("serializer", "default").toLowerCase()) {
						case "default", "mini-message", "minimessage" -> this.miniMessage;
						case "legacy", "bukkit" -> this.legacySerializer;
						default -> throw new RuntimeException("Unknown message parser for type: " + memorySection.getString("serializer"));
					};

					chatObj = memorySection.get("chat");
					String titleObj = memorySection.getString("title");
					String subtitleObj = memorySection.getString("subtitle");
					String actionBarObj = memorySection.getString("action-bar");
					//noinspection unchecked
					ComponentSerializer<Component, Component, String> serializer = (ComponentSerializer<Component, Component, String>) componentSerializer;
					Map<Message.Type, Component> componentMap = new HashMap<>();
					if (chatObj != null) {
						Component component;
						if (chatObj instanceof List) {
							List<String> list = memorySection.getStringList("chat");
							component = null;
							for (String val : list){
								if (component != null) {
									component = component.appendNewline();
									component = component.append(serializer.deserialize(val));
								} else {
									component = serializer.deserialize(val);
								}
							}
							componentMap.put(Message.Type.CHAT, component);
						} else {
							component = serializer.deserialize((String)chatObj);
							componentMap.put(Message.Type.CHAT, component);
						}
					}

					if (titleObj != null) {
						componentMap.put(Message.Type.TITLE, serializer.deserialize(titleObj));
					}

					if (subtitleObj != null) {
						componentMap.put(Message.Type.SUBTITLE, serializer.deserialize(subtitleObj));
					}

					if (actionBarObj != null) {
						componentMap.put(Message.Type.ACTION_BAR, serializer.deserialize(actionBarObj));
					}

					Map<String, Placeholder> builtInPlaceholders = this.loadPlaceholders(messageKey + ".placeholders");
					message = new Message(messageKey, componentMap, builtInPlaceholders);
					this.messagesMap.put(messageKey, message);
					return message;
				}
			}
		}
	}

	public Placeholder placeholder(String name, String value, boolean legacy) {
		return legacy ? new LegacyPlaceholder(name, value) : new Placeholder(name, value, false);
	}

	public Placeholder placeholder(String name, Component value) {
		return new Placeholder(name, value);
	}

	public MessagePlaceholder placeholderMessage(String name, String messageKey, Message.Type type) {
		Message message = this.messagesMap.get(messageKey);
		if (message == null) {
			this.loadMessage(messageKey);
			message = this.messagesMap.get(messageKey);
			if (message == null) {
				return MessagePlaceholder.emptyPlaceholder(name);
			}
		}

		return MessagePlaceholder.create(name, message, type);
	}

	public List<Placeholder> createPlaceholders(Player player){
		return PlaceholderUtils.createPlaceholders(player);
	}

	public List<Placeholder> createPlaceholders(String name, LivingEntity entity){
		return PlaceholderUtils.createPlaceholders(name, entity);
	}


	public List<Placeholder> createPlaceholders(String name, Player player){
		return PlaceholderUtils.createPlaceholders(name, player);
	}
	public List<Placeholder> createPlaceholders(String name, OfflinePlayer player){
		return PlaceholderUtils.createPlaceholders(name, player);
	}
	public List<Placeholder> createPlaceholders(String name, CommandSender sender){
		return PlaceholderUtils.createPlaceholders(name, sender);
	}
	protected Placeholder createPlaceholder(@NotNull String namespace, @NotNull String key, Object value){
		return PlaceholderUtils.createPlaceholder(namespace, key, value);
	}

	protected List<Placeholder> createPlaceholders(@NotNull String name, @NotNull String name2, @Nullable Location location) {
		return PlaceholderUtils.createPlaceholders(name, name2, location);
	}
	protected List<Placeholder> createPlaceholders(@NotNull String name, @NotNull String name2, @Nullable Biome biome) {
		return PlaceholderUtils.createPlaceholders(name, name2, biome);
	}

	protected List<Placeholder> createPlaceholders(@NotNull String name, @NotNull String name2, @Nullable ItemStack itemStack){
		return PlaceholderUtils.createPlaceholders(name, name2, itemStack);
	}


	protected Map<String, Placeholder> asPlaceholderMap(List<Placeholder> placeholders){
		if (placeholders.isEmpty()){
			return Collections.emptyMap();
		}
		Map<String, Placeholder> placeholderMap = new WeakHashMap<>();
		placeholders.forEach(pl->placeholderMap.put(pl.key(), pl));
		return placeholderMap;
	}


	@Nullable
	public Placeholder overrideDefaultPlaceholder(String key, Placeholder placeholder) {
		Map<String, Placeholder> placeholderMap = new HashMap<>(this.immutablePlaceholders);
		Placeholder oldPlaceholder = placeholderMap.put(key, placeholder);
		this.immutablePlaceholders = ImmutableMap.copyOf(placeholderMap);
		return oldPlaceholder;
	}


	public void broadcast(String  messageKey, Placeholder... placeholders){
		broadcast(null, messageKey, 0, false, List.of(placeholders));
	}
	public void broadcast(String  messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(null, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(String messageKey, List<Placeholder> placeholders) {
		broadcast(null, messageKey, 0, false, placeholders);
	}
	public void broadcast(String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(null, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Permission permission, String messageKey, Placeholder... placeholders){
		broadcast(permission, messageKey, 0, false, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(permission, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, 0, false, placeholders);
	}
	public void broadcast(Permission permission, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(String messageKey, int delay, List<Placeholder> placeholders){
		broadcast(null, messageKey, delay, false, placeholders);
	}
	public void broadcast(String messageKey, int delay, Placeholder... placeholders){
		broadcast(null, messageKey, delay, false, List.of(placeholders));
	}
	public void broadcast(String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(null, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		broadcast(null, messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Permission permission, String  messageKey, int delay, Placeholder... placeholders){
		broadcast(permission, messageKey, delay, false, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, int delay, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, delay, false, placeholders);
	}
	public void broadcast(Permission permission, String  messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(permission, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		if (permission == null) {
			permission = Permission.empty;
		}

		for (Player player : this.plugin.getServer().getOnlinePlayers()) {
			if (permission.checkPermission(player)) {
				this.message(player, messageKey, delay, placeholders);
			}
		}

		ConsoleCommandSender consoleSender = this.plugin.getServer().getConsoleSender();
		if (permission.checkPermission(consoleSender)) {
			this.message(this.plugin.getServer().getConsoleSender(), messageKey, delay, senderSpecificPlaceholders, placeholders);
		}
	}


	public void message(CommandSender to, String  messageKey, Placeholder... placeholders){
		message(null, to, messageKey, 0, false, List.of(placeholders));
	}
	public void message(CommandSender to, String  messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(null, to, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(CommandSender to, String messageKey, List<Placeholder> placeholders) {
		message(null, to, messageKey, 0, false, placeholders);
	}
	public void message(CommandSender to, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(null, to, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void message(Permission permission, CommandSender to, String messageKey, Placeholder... placeholders){
		message(permission, to, messageKey, 0, false, List.of(placeholders));
	}
	public void message(Permission permission, CommandSender to, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(permission, to, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Permission permission, CommandSender to, String messageKey, List<Placeholder> placeholders) {
		message(permission, to, messageKey, 0, false, placeholders);
	}
	public void message(Permission permission, CommandSender to, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(permission, to, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void message(CommandSender to, String messageKey, int delay, List<Placeholder> placeholders){
		message(null, to, messageKey, delay, false, placeholders);
	}
	public void message(CommandSender to, String messageKey, int delay, Placeholder... placeholders){
		message(null, to, messageKey, delay, false, List.of(placeholders));
	}
	public void message(CommandSender to, String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(null, to, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(CommandSender to, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		message(null, to, messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	public void message(Permission permission, CommandSender to, String  messageKey, int delay, Placeholder... placeholders){
		message(permission, to, messageKey, delay, false, List.of(placeholders));
	}
	public void message(Permission permission, CommandSender to, String messageKey, int delay, List<Placeholder> placeholders) {
		message(permission, to, messageKey, delay, false, placeholders);
	}
	public void message(Permission permission, CommandSender to, String  messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(permission, to, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Permission permission, CommandSender to, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		if (!this.disabledMessages.contains(messageKey)) {
			if (!permission.checkPermission(to)){
				return;
			}
			Message message = this.messagesMap.get(messageKey);
			if (message == null) {
				Message msg = this.loadMessage(messageKey);
				if (msg != null) {
					this.send(to, msg, Message.Type.CHAT, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, Message.Type.TITLE, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, Message.Type.SUBTITLE, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, Message.Type.ACTION_BAR, delay, senderSpecificPlaceholders, placeholders);
				}
			} else {
				this.send(to, message, Message.Type.CHAT, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, Message.Type.TITLE, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, Message.Type.SUBTITLE, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, Message.Type.ACTION_BAR, delay, senderSpecificPlaceholders, placeholders);
			}
		}
	}


	public @Nullable Message getMessage(String key) {
		return this.messagesMap.get(key);
	}

	public Component parse(@NotNull Message message, @NotNull Message.Type type, Placeholder... placeholders) {
		return parse(message, type, List.of(placeholders));
	}

	public Component parse(@NotNull Message message, @NotNull Message.Type type, List<Placeholder> placeholders) {
		Component messageComponent = Objects.requireNonNull(message.componentValue(type));
		String plain = PlainTextComponentSerializer.plainText().serialize(messageComponent);
		Map<String, Placeholder> placeholderMap = new WeakHashMap<>(message.placeholders());
		placeholderMap.putAll(this.immutablePlaceholders); // Built in placeholders from the message
		placeholderMap.putAll(asPlaceholderMap(placeholders));
		Matcher matcher = placeholder_pattern.matcher(plain);
		while (matcher.find()){
			@RegExp String found = plain.substring(matcher.start(), matcher.end());
			messageComponent = messageComponent.replaceText(builder -> {
				if (placeholderMap.get(found) != null) {
					Component placeholder = placeholderMap.get(found) != null ? placeholderMap.get(found).componentValue() : Component.text(found);
					builder.match(found).replacement(placeholder);
				}
			});
		}

		return messageComponent;

		// Old methods

		/*
		for (Placeholder placeholder : placeholders) {
			if (placeholder.isComponentValue()) {
				messageComponent = messageComponent.replaceText((builder) -> builder.match("%" + placeholder.key() + "%").replacement(placeholder.componentValue()));
			} else {
				messageComponent = messageComponent.replaceText((builder) -> builder.match("%" + placeholder.key() + "%").replacement(placeholder.stringValue()));
			}
		}

		for (Placeholder placeholder : this.immutablePlaceholders.values()) {
			if (placeholder.isComponentValue()) {
				messageComponent = messageComponent.replaceText((builder) -> builder.match("%" + placeholder.key() + "%").replacement(placeholder.componentValue()));
			} else {
				messageComponent = messageComponent.replaceText((builder) -> builder.match("%" + placeholder.key() + "%").replacement(placeholder.stringValue()));
			}
		}
		return messageComponent;
		 */
	}
	protected void sendConsole(final @NotNull CommandSender to, final @NotNull Message message, @NotNull final Message.@NotNull Type type, int delay, boolean senderSpecificPlaceholders, final Placeholder... placeholders) {
		sendConsole(to, message, type, delay, senderSpecificPlaceholders, List.of(placeholders));
	}

	protected void sendConsole(final @NotNull CommandSender to, final @NotNull Message message, @NotNull final Message.@NotNull Type type, int delay, boolean senderSpecificPlaceholders, final List<Placeholder> placeholders) {
		if (message.componentValue(type) != null) {
			(new BukkitRunnable() {
				public void run() {
					List<Placeholder> newPlaceholders = new ArrayList<>(placeholders);
					if (senderSpecificPlaceholders)
						newPlaceholders.addAll(createPlaceholders("player", to));
					Component messageComponent = MessageManager.this.parse(message, type, newPlaceholders);
					if (MessageManager.this.useConsoleComponentLogger) {
						ComponentLogger logger = MessageManager.this.plugin.getComponentLogger();
						logger.info(messageComponent);
					} else {
						to.sendMessage(messageComponent);
					}

				}
			}).runTaskLaterAsynchronously(this.plugin, delay);
		}
	}
	protected void send(final @NotNull CommandSender to, final @NotNull Message message, @NotNull final Message.@NotNull Type type, int delay, boolean senderSpecificPlaceholders, final Placeholder... placeholders) {
		send(to, message, type, delay, senderSpecificPlaceholders, List.of(placeholders));
	}

	protected void send(final @NotNull CommandSender to, final @NotNull Message message, @NotNull final Message.@NotNull Type type, int delay, boolean senderSpecificPlaceholders, final List<Placeholder> placeholders) {
		if (message.componentValue(type) != null) {
			if (!(to instanceof Player player)) {
				this.sendConsole(to, message, type, delay, senderSpecificPlaceholders, placeholders);
			} else {
				(new BukkitRunnable() {
					public void run() {
						List<Placeholder> newPlaceholders = new ArrayList<>(placeholders);
						if (senderSpecificPlaceholders)
							newPlaceholders.addAll(createPlaceholders("player", player));
						Component messageComponent = MessageManager.this.parse(message, type, newPlaceholders);
						switch (type) {
							case CHAT:
								to.sendMessage(messageComponent);
								break;
							case TITLE:
								to.sendTitlePart(TitlePart.TITLE, messageComponent);
								break;
							case SUBTITLE:
								to.sendTitlePart(TitlePart.SUBTITLE, messageComponent);
								break;
							case ACTION_BAR:
								to.sendActionBar(messageComponent);
						}

					}
				}).runTaskLaterAsynchronously(this.plugin, delay);
			}
		}
	}
}