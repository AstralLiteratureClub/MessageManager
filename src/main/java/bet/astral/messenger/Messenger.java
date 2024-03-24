package bet.astral.messenger;

import bet.astral.messenger.cloud.CaptionMessenger;
import bet.astral.messenger.message.adventure.AdventureMessage;
import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.message.Message;
import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.part.DefaultMessagePart;
import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.message.serializer.IMessageTypeSerializer;
import bet.astral.messenger.placeholder.*;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.C;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionProvider;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.permission.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OfflineMessage manager which loads messages in runtime and parses them.
 * @param <P> Plugin
 */
public class Messenger<P extends JavaPlugin, Comp, Audience> extends AbstractMessenger<P, Comp, Audience> implements CaptionMessenger {
	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("%([^%]+)%");
	protected final MiniMessage miniMessage = MiniMessage.miniMessage();
	protected final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();
	protected final FileConfiguration config;
	protected final Map<String, IMessage<?, Comp>> messagesMap;
	protected final Map<String, Pattern> compiledPatterns = new HashMap<>();
	protected final Map<String, Boolean> foundNotExisting = new HashMap<>();

	protected final List<String> disabledMessages;

	public Messenger(P main,
	                 Class<Comp> compClass,
	                 CommandManager<Audience> commandManager,
	                 Map<String, IMessage<IMessagePart<Comp>, Comp>> messages,
	                 @NotNull IMessageTypeSerializer<Comp> messageTypeSerializer,
	                 AudienceForwarder<Audience, Comp> forwarder,
	                 FileConfiguration fileConfiguration
	) {
		super(commandManager, main, messages, messageTypeSerializer, forwarder, compClass);
		config = fileConfiguration;
		this.disabledMessages = new LinkedList<>();
		this.messagesMap = new HashMap<>();
	}

	public boolean checkIfExists(@NotNull String messageKey){
		if (foundNotExisting.containsKey(messageKey)){
			return foundNotExisting.get(messageKey);
		}
		loadMessage(messageKey);
		return foundNotExisting.get(messageKey);
	}

	public IMessage<?, Comp> createMessage(String key, Map<MessageType, IMessagePart<Comp>> parts, Map<String, Placeholder> placeholders){
		return new Message<>(key, parts, placeholders) {
			@Override
			public Class<Comp> getType() {
				return Messenger.this.getComponentType();
			}
		};
	}


	@Override
	public @Nullable IMessage<?, Comp> getMessage(@NotNull Caption caption) {
		return messagesMap.get(caption.key());
	}
	@Override
	public @Nullable IMessage<?, Comp> loadMessage(@NotNull Caption caption) {
		IMessage<?, Comp> message = loadMessage(caption.key());
		if (message != null) {
			commandManager().captionRegistry().registerProvider(CaptionProvider.forCaption(caption, (sender) -> provide(caption, sender)));
		}
		return message;
	}

	// TODO Make so you can make messages randomized from configs
	public @Nullable IMessage<?, Comp> loadMessage(@NotNull String messageKey) {
		Object messageSection = this.config.get(messageKey);
		if (messageSection == null){
			getLogger().error("Couldn't find message key for " + messageKey + " creating a temporal message for it!");
			IMessage<?, Comp> message = new AdventureMessage(messageKey, (IMessagePart<Comp>) new DefaultMessagePart<>(MessageType.CHAT, getMessageTypeSerializer().deserialize(messageKey)));
			Message message = new Message(messageKey, Component.text(messageKey));
			messagesMap.put(messageKey, message);
			foundNotExisting.put(messageKey, false);
			return message;
		}
		IMessage<?, Comp> message;
		if (messageSection instanceof String) {
			Component messageComponent = this.miniMessage.deserialize((String)messageSection);
			message = new Message(messageKey, messageComponent);
			this.messagesMap.put(messageKey, message);
			foundNotExisting.put(messageKey, true);
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
					foundNotExisting.put(messageKey, true);
					return null;
				} else {
					message = new Message(messageKey, component);
					this.messagesMap.put(messageKey, message);
					foundNotExisting.put(messageKey, true);
					return message;
				}
			} else if (!(messageSection instanceof MemorySection memorySection)) {
				foundNotExisting.put(messageKey, false);
				throw new IllegalStateException("OfflineMessage configuration for " + messageKey + " is illegal. Please fix it!");
			} else {
				boolean disabled = memorySection.getBoolean("disabled", false);
				if (disabled) {
					this.disabledMessages.add(messageKey);
					foundNotExisting.put(messageKey, true);
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
					Map<MessageType, Component> componentMap = new HashMap<>();
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
							componentMap.put(MessageType.CHAT, component);
						} else {
							component = serializer.deserialize((String)chatObj);
							componentMap.put(MessageType.CHAT, component);
						}
					}

					if (titleObj != null) {
						componentMap.put(MessageType.TITLE, serializer.deserialize(titleObj));
					}

					if (subtitleObj != null) {
						componentMap.put(MessageType.SUBTITLE, serializer.deserialize(subtitleObj));
					}

					if (actionBarObj != null) {
						componentMap.put(MessageType.ACTION_BAR, serializer.deserialize(actionBarObj));
					}

					Object object = config.get(messageKey+".placeholders");
					if (object!=null){
						Map<String, Placeholder> builtInPlaceholders = this.loadPlaceholders(messageKey + ".placeholders");
						message = new Message(messageKey, componentMap, builtInPlaceholders);
					} else {
						message = new Message(messageKey, componentMap);
					}
					this.messagesMap.put(messageKey, message);
					foundNotExisting.put(messageKey, true);
					return message;
				}
			}
		}
	}


	protected Map<String, Placeholder> asPlaceholderMap(List<Placeholder> placeholders) {
		return getPlaceholderManager().asMap(placeholders);
	}


	public void broadcast(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		if (permission == null) {
			permission = Permission.EMPTY;
		}

		for (Player player : this.plugin.getServer().getOnlinePlayers()) {
			if (commandManager.testPermission(player, permission).allowed()) {
				this.message(player, messageKey, delay, placeholders);
			}
		}

		ConsoleCommandSender consoleSender = this.plugin.getServer().getConsoleSender();
		if (commandManager.testPermission(consoleSender, permission).allowed()) {
			this.message(this.plugin.getServer().getConsoleSender(), messageKey, delay, senderSpecificPlaceholders, placeholders);
		}
	}

	public void message(Permission permission, Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		if (!this.disabledMessages.contains(messageKey)) {
			if (permission != null) {
				if (to instanceof CommandSender sender){
					if (commandManager.testPermission(sender, permission).denied()){
						return;
					}
				} else if (to instanceof ForwardingAudience forwardingAudience){
					to = forwardingAudience.filterAudience(audience -> audience instanceof CommandSender sender &&
							commandManager.testPermission(sender, permission).allowed());
				}else {
					return;
				}
			}
			IMessage<?, Comp> message = this.messagesMap.get(messageKey);
			if (message == null) {
				IMessage<?, Comp> msg = this.loadMessage(messageKey);
				if (msg != null) {
					this.send(to, msg, MessageType.CHAT, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, MessageType.TITLE, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, MessageType.SUBTITLE, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, MessageType.ACTION_BAR, delay, senderSpecificPlaceholders, placeholders);
				}
			} else {
				this.send(to, message, MessageType.CHAT, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, MessageType.TITLE, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, MessageType.SUBTITLE, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, MessageType.ACTION_BAR, delay, senderSpecificPlaceholders, placeholders);
			}
		}
	}


	public @Nullable IMessage<?, Comp> getMessage(@NotNull String key) {
		return this.messagesMap.get(key);
	}

	public Comp parse(@NotNull IMessage<?, Comp> message, @NotNull MessageType type, Placeholder... placeholders) {
		return parse(message, type, List.of(placeholders));
	}


	public Comp parse(@NotNull IMessage<?, Comp> message, @NotNull MessageType type, List<Placeholder> placeholders) {
		C messageComponent = Objects.requireNonNull(message.parts().get(type).asComponent());
		String plain = PlainTextComponentSerializer.plainText().serialize(messageComponent);
		Map<String, Placeholder> placeholderMap = new WeakHashMap<>(this.immutablePlaceholders != null ? this.immutablePlaceholders : Collections.emptyMap());
		if (!message.placeholders().isEmpty()){
			placeholderMap.putAll(message.placeholders()); // Built in placeholders from the message
		}
		if (placeholders!=null && !placeholders.isEmpty()){
			placeholderMap.putAll(asPlaceholderMap(placeholders));
		}

		AtomicReference<Component> finalMessageComponent = new AtomicReference<>(messageComponent);
		Matcher matcher = PLACEHOLDER_PATTERN.matcher(plain);
		while (matcher.find()){
			String name = matcher.group().substring(1, matcher.group().length()-1);
			Pattern pattern = compiledPatterns.get(name.toLowerCase());
			if (pattern == null){
				pattern = Pattern.compile("%(?i)"+name+"%");
				compiledPatterns.put(name.toLowerCase(), pattern);
			}
			Placeholder placeholder = placeholderMap.get(name);
			if (placeholder == null){
				continue;
			}

			Pattern finalPattern = pattern;
			finalMessageComponent.set(finalMessageComponent.get().replaceText(builder->{
				builder.match(finalPattern).replacement(placeholder.componentValue());
			}));
		}
		return finalMessageComponent.get();
	}

	public List<Component> parseAsList(@NotNull String key, List<Placeholder> placeholders) {
		List<String> strings = config.getStringList(key);
		List<Component> components = new ArrayList<>();
		for (String string : strings) {
			Component messageComponent = miniMessage.deserialize(string);
			String plain = PlainTextComponentSerializer.plainText().serialize(messageComponent);
			Map<String, Placeholder> placeholderMap = new WeakHashMap<>(this.immutablePlaceholders != null ? this.immutablePlaceholders : Collections.emptyMap());

			if (placeholders != null && !placeholders.isEmpty()) {
				placeholderMap.putAll(asPlaceholderMap(placeholders));
			}

			AtomicReference<Component> finalMessageComponent = new AtomicReference<>(messageComponent);
			Matcher matcher = PLACEHOLDER_PATTERN.matcher(plain);
			while (matcher.find()) {
				String name = matcher.group().substring(1, matcher.group().length() - 1);
				Pattern pattern = compiledPatterns.get(name.toLowerCase());
				if (pattern == null) {
					pattern = Pattern.compile("%(?i)" + name + "%");
					compiledPatterns.put(name.toLowerCase(), pattern);
				}
				Placeholder placeholder = placeholderMap.get(name);
				if (placeholder == null) {
					continue;
				}

				Pattern finalPattern = pattern;
				finalMessageComponent.set(finalMessageComponent.get().replaceText(builder -> {
					builder.match(finalPattern).replacement(placeholder.componentValue());
				}));
			}
		}
		return components;
	}

	protected void sendConsole(final @NotNull CommandSender to, final @NotNull IMessage<?, Comp> message, @NotNull final MessageType type, int delay, boolean senderSpecificPlaceholders, final List<Placeholder> placeholders) {
		if (message.componentValue(type) != null) {
			(new BukkitRunnable() {
				public void run() {
					List<Placeholder> newPlaceholders = new ArrayList<>(placeholders);
					if (senderSpecificPlaceholders)
						newPlaceholders.addAll(createPlaceholders("player", to));
					Component messageComponent = Messenger.this.parse(message, type, newPlaceholders);
					if (Messenger.this.useConsoleComponentLogger) {
						ComponentLogger logger = Messenger.this.plugin.getComponentLogger();
						logger.info(messageComponent);
					} else {
						to.sendMessage(messageComponent);
					}

				}
			}).runTaskLaterAsynchronously(this.plugin, delay);
		}
	}
	protected void send(final @NotNull Audience to, final @NotNull IMessage<?, Comp> message, @NotNull final MessageType type, int delay, boolean senderSpecificPlaceholders, final Placeholder... placeholders) {
		send(to, message, type, delay, senderSpecificPlaceholders, List.of(placeholders));
	}

	protected void send(final @NotNull Audience to, final @NotNull IMessage<?, Comp> message, @NotNull final MessageType type, int delay, boolean senderSpecificPlaceholders, final List<Placeholder> placeholders) {
		if (message.componentValue(type) != null) {
			if (to instanceof ForwardingAudience forwardingAudience){
				for (Audience audience : forwardingAudience.audiences()){
					send(audience, message, type, delay, senderSpecificPlaceholders, placeholders);
				}
				return;
			} else if (!(to instanceof Player player)) {
				this.sendConsole((CommandSender) to, message, type, delay, senderSpecificPlaceholders, placeholders);
			} else {
				(new BukkitRunnable() {
					public void run() {
						List<Placeholder> newPlaceholders = new ArrayList<>(placeholders);
						if (senderSpecificPlaceholders)
							newPlaceholders.addAll(createPlaceholders("player", player));
						Component messageComponent = Messenger.this.parse(message, type, newPlaceholders);
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


	public ComponentLogger getLogger(){
		return plugin.getComponentLogger();
	}

	@NotNull
	@Override
	public Comp formatCaption(@NonNull Caption captionKey, Object recipient, @NonNull String caption, @NonNull List<CaptionVariable> list) {
		if (getMessage(captionKey)==null){
			return messageTypeSerializer.deserialize("");
		}
		return parse(Objects.requireNonNull(getMessage(captionKey)), MessageType.CHAT, Placeholder.of(list));
	}

	@Override
	public @org.checkerframework.checker.nullness.qual.Nullable String provide(@NonNull Caption caption, @NotNull Object recipient) {
		return null;
	}
}