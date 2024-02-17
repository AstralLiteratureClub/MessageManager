# Message Manager
Message manager is used to develop plugins faster to PaperMC based servers.
Servers without the AdventureAPI implemented natively will not be supported
as using BukkitAudiences does not fit this project.
It uses components and allows using placeholders.
The message manager takes inputs like `message.key.one` or `message.key.server` and allows server owners
to define the values for the message in a custom configuration file method.

## Placeholders
Placeholders can be defined globally by the API users or by the server owners who use the message manager.
It has a section in each message to define a placeholder (or however many you need).
Plugin developers can define their own placeholders globally and locally too per message.
Plugin developers can use ``new Placeholder(String, Value)``
and ``new LegacyPlaceholder(String, String)`` to create placeholders.

*(Other placeholder plugins are not supported currently, but adding support for other placeholder plugins would be ideal)*

## Configuration Layout

```yml
placeholders:
  - name: prefix
    value: "<red><b>Astral<reset>"
    # This defaults to "type: default"
  - name: legacy-example
    value: "&c&lAstral&r" # Need to end every message with &r to reduce the risk of mini message parsing incorrectly
    # Legacy serializers don't support "§" only "&"
    type: legacy # This parses the message using only the message parser. It does not use mini-message
  - name: message-example
    value: "combat.enter.chat"
    type: message
    message-part: chat
  - name: default-example
    value: "<red><b>Astral<reset>"
    type: default

combat:
  enter:
    enabled: true
    chat: "%prefix% <red>You're in combat! Do not log out!"
    action-bar: "<red>You are currently in combat!"
    title: "<red><b>COMBAT"
    subtitle: "<gray>You have been hit! You are now in combat!"
  leave: "%prefix% <red>You're no longer in combat!"
  log:
    enabled: false


join-message:
  # Legacy serializers don't support "§" only "&"
  serializer: legacy # Doesn't use any of mini-message to serialize given formats. Placeholders support their own serializers
  chat: "%legacy-prefix% %player% &cleft the &7server"

quit-message:
  enabled: false

chat-format:
  serializer: legacy
  chat: "%luckperms_prefix%%player%%luckperms_suffix5 &7> &f%message%"
  # Built in placeholders, so they can define something if x thing is not set.
  # Even could build the message using placeholders, but it would take performance
  placeholders:
    - name: luckperms_prefix #If luckperms isn't found in the server, these placeholders will replace the placeholders
      value: ""
      type: default
    - name: luckperms_suffix
      value: ""
    - name: antsfactions_clan
      value: "&8[&7/Clan&8]"
      type: "legacy"

faction-join:
  join:
    chat:
      - ""
      - "You have joined a new faction!"
      - ""
```

### API
Next up is showing how to define a new message manager and how to use the plugin.

Creating a new message manager in a plugin

```java
public class ExamplePlugin extends JavaPlugin {
	// It's possible to define an online message manager or use the offline message manager which supports sending offline players messages (when they join) or to send online players messages
	// MessageManager<ExamplePlugin> or OfflineMessageManager<ExamplePlugin>
	private OfflineMessageManager<ExamplePlugin> messenger; // Pick between this (only online support)
	private MessageManager<ExamplePlugin> messenger; // Or this (online and offline support)
	// Offline message manager requires a message database with an offline player database
    private MessageDatabase<ExamplePlugin> messageDb;
    private PlayerDatabase<ExamplePlugin> playerDb;

	public void onEnable() {
		// 10/01/2024
		// Databases are not fully implemented and exist as an abstract class on the writing of this readme.dm
        messageDb = MessageDatabase.createDatabaseInstance(this, DatabaseType.H2); // Required on the offline manager only
		playerDb = PlayerDatabase.createDatabaseInstance(this, DatabaseType.H2); // Required on the offline manager only
		
		File file = new File(messenger.getDataFolder(), "messages.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        // Use the constructor based on the type you chose.
		messenger = new MessageManager(this, config, new HashMap<>(), "placeholders");
		messenger = new OfflineMessageManager(this, config, new HashMap<>(), "placeholders", messageDb, playerDb);
		
	}
	
	public void onPlayerJoin(PlayerJoinEvent event){
		messenger.message(event.getPlayer(), "join", true);
		Permission permission = Permission.of("rank.developer");
		
		// Creating new placeholder
		Placeholder placeholder = new Placeholder("developer", "<red><b>Dev");
		// If they don't have the permission, just apply an empty placeholder for the name 
		if (!permission.checkPermission(event.getPlayer())){
			placeholder = Placeholder.empty("developer");
		}
		
        //
		messenger.message(permission, event.getPlayer(), "join", true, placeholder);
	}
}
```

## Extending MessageManager
This is an example pulled from my factions plugin I am developing.
I am overriding methods to send player specific placeholders to support faction placeholders and other user placeholders

```java
package bet.astral.factions.messenger;

import bet.astral.messenger.Messenger;

public interface MessageLoader {
	default void loadMessage(String key, MessageManager<?> messageManager) {
		messageManager.loadMessage(key);
	}
}
```

```java
package bet.astral.factions.messenger;

import bet.astral.factions.AstralFactions;
import bet.astral.factions.manager.FactionManager;
import bet.astral.factions.manager.UserManager;
import bet.astral.factions.model.FUser;
import bet.astral.factions.model.Faction;
import bet.astral.messenger.Message;
import bet.astral.messenger.database.DatabaseType;
import bet.astral.messenger.database.MessageDatabase;
import bet.astral.messenger.database.PlayerDatabase;
import bet.astral.messenger.offline.OfflineMessenger;
import bet.astral.messenger.placeholder.Placeholder;
import me.antritus.astral.cosmiccapital.api.providers.CosmicCapitalProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Messenger extends OfflineMessageManager<AstralFactions> implements MessageLoader {
	private final AstralFactions main;

	public Messenger(AstralFactions main, FileConfiguration messageConfig, DatabaseType databaseType) {
		this(main, messageConfig, new HashMap<>(), "placeholders", databaseType);
	}

	public Messenger(AstralFactions main, FileConfiguration messageConfig, Map<String, Message> map, String defaultPlaceholders, DatabaseType databaseType) {
		super(main, messageConfig, map, defaultPlaceholders, MessageDatabase.createDatabaseInstance(main, databaseType), PlayerDatabase.createDatabaseInstance(main, databaseType));
		this.main = main;
	}

	@Override
	public List<Placeholder> createPlaceholders(Player player) {
		return this.createPlaceholders("player", player);
	}

	@Override
	public List<Placeholder> createPlaceholders(String name, LivingEntity entity) {
		if (entity instanceof Player player) {
			return this.createPlaceholders(name, player);
		}
		return super.createPlaceholders(name, entity);
	}

	@Override
	public List<Placeholder> createPlaceholders(String name, Player player) {
		List<Placeholder> placeholders = new LinkedList<>(super.createPlaceholders(name, player));

		UserManager userManager = main.userManager();
		FUser user = userManager.getKnownUser(player);
		if (user.getFactionId() == null) {
			return placeholders;
		}

		return placeholders;
	}

	public List<Placeholder> createPlaceholders(@NotNull String name, @NotNull FUser user) {
		List<Placeholder> placeholders = new LinkedList<>();
		if (user.getFactionId() != null) {
			FactionManager factionManager = main.factionManager();
			Faction faction = factionManager.getById(user.getFactionId());
			if (faction != null) {
				placeholders.add(createPlaceholder(name, "faction", faction.getTag()));
				placeholders.addAll(createPlaceholders(name, faction));
			}
		}

		return placeholders;
	}

	public List<Placeholder> createPlaceholders(@NotNull String name, @NotNull Faction faction) {
		List<Placeholder> placeholders = new LinkedList<>();
		placeholders.add(createPlaceholder(name, "tag", faction.getTag()));
		placeholders.add(createPlaceholder(name, "id", faction.getUniqueId()));
		placeholders.add(createPlaceholder(name, "displayname", faction.getDisplayName()));
		placeholders.add(createPlaceholder(name, "members_size", faction.getMembers().size()));
		placeholders.add(createPlaceholder(name, "online_members_size", faction.getOnlineMembers().size()));
		// todo Online members and all members
		placeholders.add(createPlaceholder(name, "bank_balance", faction.getBank().balance(CosmicCapitalProvider.getAPI().mainCurrency())));
		placeholders.add(createPlaceholder(name, "power", faction.getPower()));
		placeholders.add(createPlaceholder(name, "header", faction.getHeader()));
		placeholders.add(createPlaceholder(name, "footer", faction.getFooter()));
		placeholders.add(createPlaceholder(name, "description", faction.getDescription()));
		placeholders.add(createPlaceholder(name, "created", faction.getCreated()));
		placeholders.add(createPlaceholder(name, "displayname", faction.getDisplayName()));
		placeholders.add(createPlaceholder(name, "bases", faction.getBases().size()));
		placeholders.add(createPlaceholder(name, "last_online", faction.getLastOnline()));
		placeholders.add(createPlaceholder(name, "max_allies", faction.getMaxAllies()));
		placeholders.add(createPlaceholder(name, "max_balance", faction.getMaxBalance()));
		placeholders.add(createPlaceholder(name, "max_enemies", faction.getMaxEnemies()));
		placeholders.add(createPlaceholder(name, "max_wars", faction.getMaxWars()));
		placeholders.add(createPlaceholder(name, "wars", faction.getWars().size()));
		placeholders.add(createPlaceholder(name, "allies", faction.getAllies().size()));
		placeholders.add(createPlaceholder(name, "enemies", faction.getEnemies().size()));
		placeholders.add(createPlaceholder(name, "original_owner_id", faction.getOriginalOwner()));
		placeholders.add(createPlaceholder(name, "original_owner_name", faction.getOriginalOwner()));
		return placeholders;
	}

	public void message(CommandSender commandSender, String key, boolean defaultPlaceholders, Placeholder... placeholders) {

	}

	public void message(CommandSender commandSender, String key, boolean defaultPlaceholders, List<Placeholder> placeholders) {
		if (commandSender instanceof Player entity) {
			placeholders = new LinkedList<>(placeholders);
			placeholders.addAll(createPlaceholders("player", entity));
		} else {
			placeholders = new LinkedList<>(placeholders);
		}
		message(commandSender, key, defaultPlaceholders, placeholders.toArray(Placeholder[]::new));
	}

}

```
