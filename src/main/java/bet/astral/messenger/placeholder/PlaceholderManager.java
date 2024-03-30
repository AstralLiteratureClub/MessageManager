package bet.astral.messenger.placeholder;

import bet.astral.messenger.utils.PlaceholderUtils;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PlaceholderManager {
	private final MiniMessage miniMessage = MiniMessage.miniMessage();
	@Getter
	private Map<String, Placeholder> placeholders;

	public PlaceholderManager(Map<String, Placeholder> placeholders) {
		this.placeholders = placeholders;
	}

	public PlaceholderManager(){
		this.placeholders = new HashMap<>();
	}

	@NotNull
	public Map<String, Placeholder> asMap(@NotNull Collection<Placeholder> placeholders){
		return PlaceholderUtils.asMap(placeholders);
	}
	@NotNull
	public Set<Placeholder> asSet(@NotNull Map<String, Placeholder> placeholders){
		return PlaceholderUtils.asSet(placeholders);
	}
	@NotNull
	public List<Placeholder> asList(@NotNull Map<String, Placeholder> placeholders){
		return PlaceholderUtils.asList(placeholders);
	}

	@SafeVarargs
	@NotNull
	public final List<Placeholder> combine(@NotNull Collection<Placeholder> placeholders, @Nullable Collection<Placeholder>... placeholderListArray){
		PlaceholderList placeholderList = new PlaceholderList();
		placeholders = new LinkedList<>(placeholders);
		for (Collection<Placeholder> placeholderListCop : placeholderListArray){
			if (placeholderListCop == null) {
				continue;
			}
			placeholders.addAll(placeholderListCop);
		}
		return placeholderList;
	}

	@SafeVarargs
	@NotNull
	public final Map<String, Placeholder> combine(@NotNull Map<String, Placeholder> placeholders, @Nullable Map<String, Placeholder>... placeholderListArray){
		placeholders = new HashMap<>(placeholders);
		for (Map<String, Placeholder> placeholderMap : placeholderListArray){
			if (placeholderMap == null) {
				continue;
			}
			placeholders.putAll(placeholderMap);
		}
		return placeholders;
	}



	@NotNull
	public List<Placeholder> playerPlaceholders(@Nullable String prefix, @NotNull Player player){
		return PlaceholderUtils.createPlaceholders(prefix, (LivingEntity) player);
	}

	@NotNull
	public List<Placeholder> offlinePlayerPlaceholders(@Nullable String prefix, @NotNull OfflinePlayer player){
		if (player instanceof Player p){
			return playerPlaceholders(prefix, p);
		}
		return PlaceholderUtils.createPlaceholders(prefix, player);
	}

	@NotNull
	public List<Placeholder> entityPlaceholders(@Nullable String prefix, @NotNull LivingEntity entity){
		if (entity instanceof Player player){
			return playerPlaceholders(prefix, player);
		}
		return PlaceholderUtils.createPlaceholders(prefix, entity);
	}

	@NotNull
	public List<Placeholder> placeholders(@Nullable String prefix, @NotNull Audience audience){
		if (audience instanceof CommandSender sender){
			return this.senderPlaceholders(prefix, sender);
		}
		return Collections.emptyList();
	}

	@NotNull
	public List<Placeholder> senderPlaceholders(@Nullable String prefix, @NotNull CommandSender commandSender){
		if (commandSender instanceof Player player){
			return playerPlaceholders(prefix, player);
		} else if (commandSender instanceof LivingEntity entity){
			return entityPlaceholders(prefix, entity);
		}
		return PlaceholderUtils.createPlaceholders(prefix, commandSender);
	}

	public ImmutableMap<String, Placeholder> loadPlaceholders(String key, MemorySection config) {
		Map<String, Placeholder> placeholderMap = new HashMap<>();
		List<Map<?, ?>> placeholderMapList = config.getMapList(key);

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
					/*
				case "message":
					IMessage<?, C> message = this.messagesMap.get(value);
					String messagePart = (String)map.get("message-part");
					if (messagePart == null) {
						messagePart = "chat";
					}

					messagePart = messagePart.replaceAll("-", "_");
					messagePart = messagePart.replaceAll(" ", "_");
					MessageType messageType = MessageType.valueOf(messagePart);
					if (message == null) {
						placeholder = MessagePlaceholder.emptyPlaceholder(name);
					} else {
						placeholder = MessagePlaceholder.create(name, message, messageType);
					}
					break;
					 */
				default:
					throw new RuntimeException("Unknown placeholder serializer type: " + type);
			}
			placeholderMap.put(placeholder.key(), placeholder);
		}

		return ImmutableMap.copyOf(placeholderMap);
	}

	@NotNull
	public Map<String, Placeholder> setDefaults(@NotNull Map<String, Placeholder> newPlaceholders) {
		Map<String, Placeholder> placeholderMap = placeholders;
		this.placeholders = newPlaceholders;
		return placeholderMap;
	}

	@Nullable
	public Placeholder setDefault(@NotNull Placeholder placeholder){
		return this.placeholders.put(placeholder.key(), placeholder);
	}
}
