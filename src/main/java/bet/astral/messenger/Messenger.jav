package bet.astral.messenger;

import bet.astral.messenger.adventure.AdventurePlaceholderManager;
import bet.astral.messenger.component.ComponentType;
import bet.astral.messenger.message.adventure.serializer.ComponentTitleSerializer;
import bet.astral.messenger.message.adventure.serializer.ComponentTypeSerializer;
import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.part.DefaultMessagePart;
import bet.astral.messenger.message.serializer.DefaultMessageSerializer;
import bet.astral.messenger.message.serializer.IMessageSerializer;
import bet.astral.messenger.message.serializer.IMessageTitleSerializer;
import bet.astral.messenger.message.serializer.IComponentSerializer;
import bet.astral.messenger.placeholder.*;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
@Deprecated(forRemoval = true)
public class Messenger<P extends JavaPlugin> extends AbstractMessenger<P, Component, CommandSender> {
	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("%([^%]+)%");
	protected final MiniMessage miniMessage = MiniMessage.miniMessage();
	protected final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();
	protected final FileConfiguration config;
	protected final Map<String, IMessage<?, Component>> messagesMap;
	protected final Map<String, Pattern> compiledPatterns = new HashMap<>();
	protected final Map<String, Boolean> foundNotExisting = new HashMap<>();

	protected final List<String> disabledMessages;

	public Messenger(P main,
	                 CommandManager<CommandSender> commandManager,
	                 Map<String, IMessage<IMessageComponent<Component>, Component>> messages,
	                 @NotNull IComponentSerializer<Component> messageTypeSerializer,
	                 FileConfiguration fileConfiguration
	) {
		super(commandManager, main, messages, messageTypeSerializer, Component.class);
		config = fileConfiguration;
		this.disabledMessages = new LinkedList<>();
		this.messagesMap = new HashMap<>();
		IComponentSerializer<Component> serializer = new ComponentTypeSerializer();
		setMessageTypeSerializer(serializer);
		setPlaceholderManager(new AdventurePlaceholderManager());
		getDeserializers().put("default", new DefaultMessageSerializer<>(serializer, AdventureMessage.class));
		getDeserializers().put("title", new ComponentTitleSerializer<>(serializer, AdventureMessage.class));
	}

	@Override
	public AdventurePlaceholderManager getPlaceholderManager() {
		return (AdventurePlaceholderManager) super.getPlaceholderManager();
	}

	public void setPlaceholderManager(AdventurePlaceholderManager placeholderManager){
		super.setPlaceholderManager(placeholderManager);
	}
	@Override
	public void setPlaceholderManager(PlaceholderManager placeholderManager) {
		if (placeholderManager instanceof AdventurePlaceholderManager){
			super.setPlaceholderManager(placeholderManager);
		} else {
			throw new IllegalStateException("Only adventure placeholder manager or children classes of it are allowed in Messenger!");
		}
	}

	public boolean checkIfExists(@NotNull String messageKey){
		if (foundNotExisting.containsKey(messageKey)){
			return foundNotExisting.get(messageKey);
		}
		loadMessage(messageKey);
		return foundNotExisting.get(messageKey);
	}

	public IMessage<?, Component> createMessage(String key, Map<ComponentType, IMessageComponent<Component>> parts, Map<String, Placeholder> placeholders){
		return new AdventureMessage(key, parts, placeholders);
	}

	@Override
	public @Nullable IMessage<?, Component> loadMessage(@NotNull Caption caption) {
		IMessage<?, Component> message = loadMessage(caption.key());
		if (message != null) {
			getCommandManager().captionRegistry().registerProvider(CaptionProvider.forCaption(caption, (sender) -> provide(caption, sender)));
		}
		return message;
	}

	// TODO Make so you can make messages randomized from configs
	public @Nullable IMessage<?, Component> loadMessage(@NotNull String messageKey) {
		Object messageSection = this.config.get(messageKey);
		if (messageSection == null){
			getLogger().error("Couldn't find message key for " + messageKey + " creating a temporal message for it!");
			IMessage<?, Component> message = new AdventureMessage(messageKey, new DefaultMessagePart<>(ComponentType.CHAT, getMessageTypeSerializer().deserialize(messageKey)));
			messagesMap.put(messageKey, message);
			foundNotExisting.put(messageKey, false);
			return message;
		}
		IMessage<?, Component> message;
		if (messageSection instanceof String) {
			Component messageComponent = this.miniMessage.deserialize((String)messageSection);
			message = new AdventureMessage(messageKey, new DefaultMessagePart<>(ComponentType.CHAT, messageComponent));
			this.messagesMap.put(messageKey, message);
			foundNotExisting.put(messageKey, true);
			return message;
		} else if (messageSection instanceof List<?>){
			List<String> list = this.config.getStringList(messageKey);
			IMessageSerializer<?, ?, Component> serializer = getDeserializers().get("default");
			IMessageComponent<Component> part = serializer.deserialize(list, ComponentType.CHAT);
			message = new AdventureMessage(messageKey, part);

			this.messagesMap.put(messageKey, message);
			return message;
		}else {
			Object chatObj;
			if (!(messageSection instanceof MemorySection memorySection)) {
				foundNotExisting.put(messageKey, false);
				throw new IllegalStateException("OfflineMessage configuration for " + messageKey + " is illegal. Please fix it!");
			} else {
				boolean disabled = memorySection.getBoolean("disabled", false);
				if (disabled) {
					this.disabledMessages.add(messageKey);
					foundNotExisting.put(messageKey, true);
					message = new AdventureMessage(messageKey, new DefaultMessagePart<>(ComponentType.CHAT));
					this.messagesMap.put(messageKey, message);
					return null;
				} else {
					chatObj = memorySection.get("chat");
					String titleObj = memorySection.getString("title");
					String subtitleObj = memorySection.getString("subtitle");
					String actionBarObj = memorySection.getString("action-bar");

					Map<ComponentType, IMessageComponent<Component>> componentMap = new HashMap<>();
					if (chatObj != null) {
						if (chatObj instanceof List) {
							List<String> list = memorySection.getStringList("chat");
							IMessageSerializer<?, ?, Component> serializer = getDeserializers().get("default");
							IMessageComponent<Component> part = serializer.deserialize(list, ComponentType.CHAT);

							componentMap.put(ComponentType.CHAT, part);
						} else {
							componentMap.put(ComponentType.CHAT, tryToLoad(chatObj, ComponentType.CHAT));
						}
					}

					if (titleObj != null) {
						componentMap.put(TITLE, tryToLoad(titleObj, TITLE));
					}

					if (subtitleObj != null) {
						componentMap.put(ComponentType.SUBTITLE, tryToLoad(subtitleObj, ComponentType.SUBTITLE));
					}

					if (actionBarObj != null) {
						componentMap.put(ComponentType.ACTION_BAR, tryToLoad(actionBarObj, ComponentType.ACTION_BAR));
					}

					Object object = config.get(messageKey+".placeholders");
					if (object!=null){
						Map<String, Placeholder> builtInPlaceholders = this.loadPlaceholder(messageKey + ".placeholders", config);
						message = new AdventureMessage(messageKey, componentMap, builtInPlaceholders);
					} else {
						message = new AdventureMessage(messageKey, componentMap);
					}
					this.messagesMap.put(messageKey, message);
					foundNotExisting.put(messageKey, true);
					return message;
				}
			}
		}
	}
	@Nullable
	private IMessageComponent<Component> tryToLoad(Object object, ComponentType type){
		if (object instanceof MemorySection section){
			String serializerType = section.getString("serializer", type == TITLE || type == SUBTITLE ? "title" : "default");

			IMessageSerializer<?, ?, Component> serializer = getDeserializers().get(serializerType);
			if (serializer == null){
				serializer = getDeserializers().get("default");
			}
			if (section.get("value") == null){
				throw new IllegalArgumentException("Couldn't find value for message with key " + section.getCurrentPath()+ ".");
			}

			if (section.get("value") instanceof List<?>){
				if (type== TITLE || type == ComponentType.SUBTITLE) {
					if (serializer instanceof IMessageTitleSerializer<?, ?> titleSerializer){
						//noinspection unchecked
						return (IMessageComponent<Component>) titleSerializer.load(section, type);
					}
					return serializer.deserialize(section.getStringList("value"), type);
				}
				return serializer.deserialize(section.getStringList("value"), type);
			} else if (section.get("value") instanceof String){
				if (type== TITLE || type == ComponentType.SUBTITLE) {
					if (serializer instanceof IMessageTitleSerializer<?, ?> titleSerializer){
						//noinspection unchecked
						return (IMessageComponent<Component>) titleSerializer.load(section, type);
					}
					return serializer.deserialize(section.getStringList("value"), type);
				}
				return serializer.deserialize(Objects.requireNonNull(section.getString("value")), type);
			} else {
				return null;
			}
		} else if (object instanceof String s){
			IMessageSerializer<?, ?, Component> serializer = getDeserializers().get(type == TITLE ? "title" : "default");
			return serializer.deserialize(s, type);
		}
		return null;
	}


	protected Map<String, Placeholder> asPlaceholderMap(List<Placeholder> placeholders) {
		return getPlaceholderManager().asMap(placeholders);
	}


	public void broadcast(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		if (permission == null) {
			permission = Permission.EMPTY;
		}

		for (Player player : getMain().getServer().getOnlinePlayers()) {
			if (getCommandManager().testPermission(player, permission).allowed()) {
				this.message(player, messageKey, delay, placeholders);
			}
		}

		ConsoleCommandSender consoleSender = getMain().getServer().getConsoleSender();
		if (getCommandManager().testPermission(consoleSender, permission).allowed()) {
			this.message(consoleSender, messageKey, delay, senderSpecificPlaceholders, placeholders);
		}
	}

	public void message(Permission permission, Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		if (!this.disabledMessages.contains(messageKey)) {
			if (permission != null) {
				if (to instanceof CommandSender sender){
					if (getCommandManager().testPermission(sender, permission).denied()){
						return;
					}
				} else if (to instanceof ForwardingAudience forwardingAudience){
					to = forwardingAudience.filterAudience(audience -> audience instanceof CommandSender sender &&
							getCommandManager().testPermission(sender, permission).allowed());
				}else {
					return;
				}
			}
			IMessage<?, Component> message = this.messagesMap.get(messageKey);
			if (message == null) {
				IMessage<?, Component> msg = this.loadMessage(messageKey);
				if (msg != null) {
					this.send(to, msg, ComponentType.CHAT, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, TITLE, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, ComponentType.SUBTITLE, delay, senderSpecificPlaceholders, placeholders);
					this.send(to, msg, ComponentType.ACTION_BAR, delay, senderSpecificPlaceholders, placeholders);
				}
			} else {
				this.send(to, message, ComponentType.CHAT, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, TITLE, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, ComponentType.SUBTITLE, delay, senderSpecificPlaceholders, placeholders);
				this.send(to, message, ComponentType.ACTION_BAR, delay, senderSpecificPlaceholders, placeholders);
			}
		}
	}


	public @Nullable IMessage<?, Component> getMessage(@NotNull String key) {
		return this.messagesMap.get(key);
	}

	@Override
	public @NonNull Component formatCaption(@NonNull Caption captionKey, @NonNull CommandSender recipient, @NonNull String caption, @NonNull List<@NonNull CaptionVariable> variables) {
		return parse(getMessage(caption), ComponentType.CHAT, Placeholder.of(variables));
	}

	@Override
	public @org.checkerframework.checker.nullness.qual.Nullable String provide(@NonNull Caption caption, @NonNull CommandSender recipient) {
		return null;
	}

	public Component parse(@NotNull IMessage<?, Component> message, @NotNull ComponentType type, Placeholder... placeholders) {
		return parse(message, type, List.of(placeholders));
	}


	@Override
	@Nullable
	public Component parse(@NotNull IMessage<?, Component> message, @Nullable ComponentType type, List<Placeholder> placeholders) {
		IMessageComponent<Component> part = message.parts().get(type);
		if (part == null){
			return null;
		}
		Component component = part.asComponent();
		if (component == null){
			return null;
		}
		String plain = PlainTextComponentSerializer.plainText().serialize(component);
		Map<String, Placeholder> placeholderMap = getPlaceholderManager()
				.combine(
						getPlaceholderManager().getPlaceholders(),
						asPlaceholderMap(placeholders),
						message.placeholders()
				);

		AtomicReference<Component> finalMessageComponent = new AtomicReference<>(component);
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
			Map<String, Placeholder> placeholderMap = getPlaceholderManager()
					.combine(
							getPlaceholderManager().getPlaceholders(),
							asPlaceholderMap(placeholders)
					);

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

	protected void sendConsole(final @NotNull CommandSender to, final @NotNull IMessage<?, Component> message, @NotNull final ComponentType type, int delay, boolean senderSpecificPlaceholders, final List<Placeholder> placeholders) {
		IMessageComponent<Component> component = message.parts().get(type);
		if (component == null || !message.enabled()){
			return;
		}
		(new BukkitRunnable() {
			public void run() {
				List<Placeholder> newPlaceholders = new ArrayList<>(placeholders);
				if (senderSpecificPlaceholders)
					newPlaceholders.addAll(getPlaceholderManager().senderPlaceholders("player", to));
				Component messageComponent = Messenger.this.parse(message, type, newPlaceholders);
				if (messageComponent == null){
					return;
				}
				if (isConsoleLogger()){
					ComponentLogger logger = getMain().getComponentLogger();
					logger.info(messageComponent);
				} else {
					to.sendMessage(messageComponent);
				}

			}
		}).runTaskLaterAsynchronously(getMain(), delay);
	}
	protected void send(final @NotNull Audience to, final @NotNull IMessage<?, Component> message, @NotNull final ComponentType type, int delay, boolean senderSpecificPlaceholders, final Placeholder... placeholders) {
		send(to, message, type, delay, senderSpecificPlaceholders, List.of(placeholders));
	}

	protected void send(final @NotNull Audience to, final @NotNull IMessage<?, Component> message, @NotNull final ComponentType type, int delay, boolean senderSpecificPlaceholders, final List<Placeholder> placeholders) {
		IMessageComponent<Component> part = message.parts().get(type);
		if (part == null || !message.enabled()) {
			return;
		}

		if (to instanceof ForwardingAudience forwardingAudience) {
			//noinspection OverrideOnly
			for (Audience audience : forwardingAudience.audiences()) {
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
						newPlaceholders.addAll(getPlaceholderManager().audiencePlaceholders("player", player));
					Component messageComponent = Messenger.this.parse(message, type, newPlaceholders);
					if (messageComponent == null){
						return;
					}
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
			}).runTaskLaterAsynchronously(getMain(), delay);
		}
	}

	public ComponentLogger getLogger() {
		return getMain().getComponentLogger();
	}
}