package bet.astral.messenger.utils;

import bet.astral.messenger.Message;
import bet.astral.messenger.Messenger;
import bet.astral.messenger.placeholder.LegacyPlaceholder;
import bet.astral.messenger.placeholder.MessagePlaceholder;
import bet.astral.messenger.placeholder.Placeholder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public final class PlaceholderUtils {
	@NotNull
	public final static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	@NotNull
	public final static DecimalFormat decimalFormat0 = new DecimalFormat(".");
	@NotNull
	public final static DecimalFormat decimalFormat = new DecimalFormat(".0");
	@NotNull
	public final static DecimalFormat decimalFormat2 = new DecimalFormat(".00");

	private PlaceholderUtils(){}
	public static Placeholder placeholder(String name, String value, boolean legacy) {
		return legacy ? new LegacyPlaceholder(name, value) : new Placeholder(name, value, false);
	}

	public static Placeholder placeholder(String name, Component value) {
		return new Placeholder(name, value);
	}

	public static MessagePlaceholder placeholderMessage(Messenger<?> manager, String name, String messageKey, Message.Type type) {
		Message message = manager.getMessage(messageKey);
		if (message == null) {
			manager.loadMessage(messageKey);
			message = manager.getMessage(messageKey);
			if (message == null) {
				return MessagePlaceholder.emptyPlaceholder(name);
			}
		}

		return MessagePlaceholder.create(name, message, type);
	}

	public static List<Placeholder> createPlaceholders(Player player){
		return createPlaceholders("player", player);
	}

	public static List<Placeholder> createPlaceholders(String name, CommandSender commandSender){
		if (commandSender instanceof Player player){
			return createPlaceholders(name, player);
		} else if (commandSender instanceof LivingEntity entity){
			return createPlaceholders(name, entity);
		}
		List<Placeholder> placeholders = new LinkedList<>();
		placeholders.add(createPlaceholder(name, "name", commandSender.getName()));
		return placeholders;
	}
	public static List<Placeholder> createPlaceholders(String name, LivingEntity entity){
		List<Placeholder> placeholders = new LinkedList<>();
		placeholders.add(createPlaceholder(name,"uuid", entity.getUniqueId())); // unique id
		placeholders.add(createPlaceholder(name,"id", entity.getEntityId())); // id -
		// this is not unique id it's the entity id in the server
		placeholders.addAll(createPlaceholders(name, "entity_id", entity.getLocation())); // location
		placeholders.add(createPlaceholder(name, "absorption", entity.getAbsorptionAmount())); // current absorption
		placeholders.add(createPlaceholder(name, "max_absorption", entity.getAttribute(Attribute.GENERIC_MAX_ABSORPTION))); // max absorption
		placeholders.add(createPlaceholder(name,"health", entity.getHealth())); // current health
		placeholders.add(createPlaceholder(name,"max_health", entity.getAttribute(Attribute.GENERIC_MAX_HEALTH))); // max health


		if (entity.getEquipment() != null){
			EntityEquipment entityEquipment = entity.getEquipment();
			placeholders.addAll(createPlaceholders(name, "armor_helmet", entityEquipment.getHelmet())); // helmet
			placeholders.addAll(createPlaceholders(name, "armor_chestplate", entityEquipment.getHelmet())); // chestplate
			placeholders.addAll(createPlaceholders(name, "armor_leggings", entityEquipment.getHelmet())); // leggings
			placeholders.addAll(createPlaceholders(name, "armor_boots", entityEquipment.getHelmet())); // boots
			placeholders.addAll(createPlaceholders(name, "item_in_hand", entityEquipment.getHelmet())); // main hand
			placeholders.addAll(createPlaceholders(name, "item_in_offhand", entityEquipment.getHelmet())); // offhand
			if (entity instanceof Player player) {
				placeholders.add(createPlaceholder(name, "has_empty_slot", hasEmptySlot(player.getInventory())));
				placeholders.add(createPlaceholder(name, "empty_slots", emptySlots((player.getInventory()))));
			}else {
				placeholders.add(createPlaceholder(name, "has_empty_slot", hasEmptySlot(entityEquipment)));
				placeholders.add(createPlaceholder(name, "has_empty_slot", emptySlots(entityEquipment)));
			}

		}
		placeholders.add(createPlaceholder(name,"block_underneath", entity.getWorld().getBlockAt(entity.getLocation()).getRelative(BlockFace.DOWN))); // block below entity
		placeholders.add(createPlaceholder(name,"can_pickup_items", entity.getCanPickupItems())); // can pick items
		placeholders.add(createPlaceholder(name, "custom_name", entity.customName() != null ? entity.customName() : entity.name())); // custom name
		placeholders.add(createPlaceholder(name,"direction", direction(false, direction(entity.getLocation().getYaw())))); // direction | long -> NORTH,
		// SOUTH, WEST, etc
		placeholders.add(createPlaceholder(name,"direction_short", direction(true, direction(entity.getLocation().getYaw())))); // direction | short -> N,
		// S, W, etc
		placeholders.add(createPlaceholder(name,"has_health_boost", entity.getPotionEffect(PotionEffectType.HEALTH_BOOST) != null));
		for (PotionEffectType type : PotionEffectType.values()){
			String effectName = type.key().value().toLowerCase();
			placeholders.add(createPlaceholder(name,"has_potion_effect_"+effectName, entity.hasPotionEffect(type))); // has potion effect
			placeholders.add(createPlaceholder(name,"potion_effect_"+effectName, entity.getPotionEffect(type) != null ? Objects.requireNonNull(entity.getPotionEffect(type)).getAmplifier() : 0)); // potion effect amplifier
			placeholders.add(createPlaceholder(name,"potion_effect_duration_"+effectName, entity.getPotionEffect(type) != null ? Objects.requireNonNull(entity.getPotionEffect(type)).getDuration() : 0)); // potion effect duration
		}
		placeholders.add(createPlaceholder(name,"is_sneaking", entity.isSneaking()));
		placeholders.add(createPlaceholder(name,"is_sleeping", entity.isSleeping()));
		placeholders.add(createPlaceholder(name,"is_inside_vehicle", entity.isInsideVehicle()));
		placeholders.add(createPlaceholder(name,"is_op", entity.isOp()));

		placeholders.add(createPlaceholder(name,"last_damage", entity.getLastDamage()));
		placeholders.add(createPlaceholder(name,"last_damage_cause", entity.getLastDamageCause() != null ? improveName(entity.getLastDamageCause().getCause().name()) : "None"));
		placeholders.add(createPlaceholder(name, "light_level", entity.getEyeLocation().getBlock().getLightLevel()));
		placeholders.add(createPlaceholder(name,"max_air", entity.getMaximumAir()));
		placeholders.add(createPlaceholder(name,"air", entity.getRemainingAir()));
		placeholders.add(createPlaceholder(name,"max_no_damage_ticks", entity.getMaximumNoDamageTicks()));
		placeholders.add(createPlaceholder(name,"no_damage_ticks", entity.getNoDamageTicks()));
		placeholders.add(createPlaceholder(name,"seconds_lived", entity.getTicksLived()/20));
		placeholders.add(createPlaceholder(name,"minutes_lived", entity.getTicksLived()/20/60));
		placeholders.add(createPlaceholder(name,"ticks_lived", entity.getTicksLived()));
		if (entity instanceof Player player){
			placeholders.add(createPlaceholder(name,"health_scale", player.getHealthScale())); // health scale sent to the target
			if (isValid(player.getPotentialBedLocation(), "bed"))
				placeholders.addAll(createPlaceholders(name, "bed", player.getPotentialBedLocation())); // bed locationb
			placeholders.addAll(createPlaceholders(name, "compass", player.getCompassTarget())); // compass target location
			placeholders.add(createPlaceholder(name, "displayname", player.displayName())); // displayname
			placeholders.add(createPlaceholder(name, "nickname", player.displayName())); // displayname
			placeholders.add(createPlaceholder(name, "current_exp", player.getExp())); // current experience
			placeholders.add(createPlaceholder(name, "exp_required", player.getExpToLevel())); // required experience to level
			placeholders.add(createPlaceholder(name, "total_exp", player.getTotalExperience())); // total experience
			placeholders.add(createPlaceholder(name, "fly_speed", player.getFlySpeed())); // fly speed
			placeholders.add(createPlaceholder(name, "walk_speed", player.getWalkSpeed())); // walk speed
			placeholders.add(createPlaceholder(name, "food_level", player.getFoodLevel())); // food level
			placeholders.add(createPlaceholder(name, "saturation", player.getSaturation())); // saturation
			placeholders.add(createPlaceholder(name, "saturation_regen_rate", player.getSaturatedRegenRate())); // saturation regen

			GameMode gameMode = player.getGameMode();
			placeholders.add(createPlaceholder(name, "gamemode", Component.translatable(gameMode))); // gamemode as translatable
			placeholders.add(createPlaceholder(name, "gamemode_eng", gameMode == GameMode.CREATIVE ? "Creative" : gameMode == GameMode.SURVIVAL ? "Survival" : gameMode == GameMode.SPECTATOR ? "Spectator" : "Adventure")); // english gamemodes

			placeholders.add(createPlaceholder(name,"is_flying", player.isFlying()));
			placeholders.add(createPlaceholder(name,"is_sprinting", player.isSprinting()));
			placeholders.add(createPlaceholder(name,"is_sleeping_deeply", player.isDeeplySleeping()));
			placeholders.add(createPlaceholder(name,"is_sleeping_ignored", player.isSleepingIgnored()));

			placeholders.add(createPlaceholder(name,"locale", player.locale()));
			placeholders.add(createPlaceholder(name,"locale_display_name", player.locale().getDisplayName()));
			placeholders.add(createPlaceholder(name,"locale_short", player.locale().toString().contains("_")?player.locale().toString().split("_")[0]:player.locale().toString()));
			placeholders.add(createPlaceholder(name,"locale_country", player.locale().getCountry()));
			placeholders.add(createPlaceholder(name,"locale_display_country", player.locale().getDisplayCountry()));

			placeholders.add(createPlaceholder(name,"level", player.getLevel()));
			placeholders.add(createPlaceholder(name,"sleep_ticks", player.getSleepTicks()));
			placeholders.add(createPlaceholder(name,"idle_duration", player.getIdleDuration()));
			placeholders.add(createPlaceholder(name,"time", player.getPlayerTime()));
			placeholders.add(createPlaceholder(name,"time_offset", player.getPlayerTimeOffset()));

			placeholders.add(createPlaceholder(name,"warden_warning_level", player.getWardenWarningLevel()));
			placeholders.add(createPlaceholder(name,"warden_warning_cooldown", player.getWardenWarningCooldown()));
			placeholders.add(createPlaceholder(name,"warden_time_since_warning", player.getWardenTimeSinceLastWarning()));

			placeholders.addAll(createPlaceholders(name, (OfflinePlayer) player));
		}

		return placeholders;
	}


	public static List<Placeholder> createPlaceholders(String name, Player player){
		return createPlaceholders(name, (LivingEntity) player);
	}

	public static List<Placeholder> createPlaceholders(@NotNull String name, @NotNull OfflinePlayer player){
		List<Placeholder> placeholders = new LinkedList<>(createPlaceholders(name, (LivingEntity) player));
		placeholders.add(createPlaceholder(name, "first_join", dateFormat.format(Instant.ofEpochSecond(player.getFirstPlayed())))); // first join formatted
		placeholders.add(createPlaceholder(name, "first_join_unix", player.getFirstPlayed())); // first join in default form
		placeholders.add(createPlaceholder(name, "last_played", dateFormat.format(Instant.ofEpochSecond(player.getLastSeen())))); // last time
		// player was online formatted
		placeholders.add(createPlaceholder(name, "last_played_unix", player.getLastSeen())); // last time player was online
		placeholders.add(createPlaceholder(name, "has_played_before", player.hasPlayedBefore())); // has played before
		placeholders.add(createPlaceholder(name,"is_whitelisted", player.isWhitelisted()));
		placeholders.add(createPlaceholder(name,"is_banned", player.isBanned()));
		return placeholders;
	}

	public static Placeholder createPlaceholder(@NotNull String namespace, @NotNull String key, Object value){
		if (value == null){
			return Placeholder.emptyPlaceholder(namespace);
		}
		if (value instanceof Component component) {
			return new Placeholder(namespace+"_"+key, component);
		} else if (value instanceof String s){
			return new Placeholder(namespace+"_"+key, s);
		}
		return new Placeholder(namespace+"_"+key, value);
	}

	public static List<Placeholder> createPlaceholders(@NotNull String name, @NotNull String name2, @Nullable Location location) {
		if (location == null){
			return Collections.emptyList();
		}
		List<Placeholder> placeholders = new LinkedList<>();
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_x", location.getX()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_y", location.getY()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_z", location.getZ()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_yaw", location.getYaw()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_pitch", location.getPitch()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_block_x", location.getBlockX()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_block_y", location.getBlockY()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_block_z", location.getBlockZ()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_block_z", location.getBlockZ()));
		placeholders.add(createPlaceholder(name, defaultVal(name2) + "_world", location.getWorld().getName()));
		return placeholders;
	}
	public static List<Placeholder> createPlaceholders(@NotNull String name, @NotNull String name2, @Nullable Biome biome) {
		if (biome == null){
			return Collections.emptyList();
		}
		List<Placeholder> placeholders = new LinkedList<>();
		placeholders.add(createPlaceholder(name, defaultVal(name2), improveKeyName(biome)));
		placeholders.add(createPlaceholder(name, defaultVal(name2) +"_uppercase", improveKeyName(biome).toUpperCase()));
		return placeholders;
	}

	public static List<Placeholder> createPlaceholders(@NotNull String name, @NotNull String name2, @Nullable ItemStack itemStack){
		if (itemStack == null){
			return Collections.emptyList();
		}
		List<Placeholder> placeholders = new LinkedList<>();
		ItemMeta meta = itemStack.getItemMeta();
		Component nameComp = Component.translatable(itemStack.translationKey());
		placeholders.add(createPlaceholder(name, defaultVal(name2), nameComp));
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_name", nameComp));
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_displayname", meta.displayName() != null ? meta.displayName() : nameComp));
		//noinspection deprecation
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_data", itemStack.getData()));
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_damage", itemStack.getItemMeta() instanceof Damageable damageable ? damageable.getDamage() : 0));
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_durability_left", itemStack.getItemMeta() instanceof Damageable damageable ? itemStack.getType().getMaxDurability()-damageable.getDamage() : 0));
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_durability", itemStack.getType().getMaxDurability()));
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_meta_nbt", meta.getAsString()));
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_max_amount", itemStack.getMaxStackSize()));
		placeholders.add(createPlaceholder(name,defaultVal(name2)+"_amount", itemStack.getAmount()));
		for (Enchantment enchantment : Enchantment.values()){
			String enchName = improveKeyName(enchantment);
			int enchLevel = itemStack.getEnchantments().get(enchantment) != null ? itemStack.getEnchantments().get(enchantment) : 0;
			int enchMax = enchantment.getMaxLevel();
			//String enchTarget = enchantment.getItemTarget().name();
			//String enchRarity = enchantment.getRarity().name();
			placeholders.add(createPlaceholder(name, defaultVal(name2)+"_level_"+enchName, enchLevel));
			placeholders.add(createPlaceholder(name, defaultVal(name2)+"_enchantment_"+enchName, enchLevel));
			placeholders.add(createPlaceholder(name, defaultVal(name2)+"_enchantment_"+enchName+"_max", enchMax));
		}

		return placeholders;
	}

	public static String defaultVal(String name){
		return name.isEmpty() ? "" : name;
	}

	public static String improveKeyName(Keyed key){
		return improveKeyName(key.key());
	}
	public static String improveKeyName(Key key) {
		if (key == null) {
			return "null";
		}

		String name = key.value().replaceAll("[_-]", " ").toLowerCase();
		StringBuilder builder = new StringBuilder();

		for (String val : name.split(" ")) {
			val = val.substring(0, 1).toUpperCase() + val.substring(1);
			builder.append(!builder.isEmpty() ? " " : "").append(val);
		}

		return builder.toString();
	}

	public static String improveName(String name){
		name = name.replaceAll("[_-]", " ").toLowerCase();
		StringBuilder builder = new StringBuilder();

		for (String val : name.split(" ")) {
			val = val.substring(0, 1).toUpperCase() + val.substring(1);
			builder.append(!builder.isEmpty() ? " " : "").append(val);
		}

		return builder.toString();
	}
	public static boolean isValid(@Nullable Location location, @NotNull String material){
		if (location == null || location.getWorld() == null)
			return false;
		Block block = location.getWorld().getBlockAt(location);
		return (block.getType().name().toLowerCase().contains(material.toLowerCase()));
	}
	public static boolean isValid(@Nullable Location location, @NotNull Material material){
		if (location == null || location.getWorld() == null)
			return false;
		Block block = location.getWorld().getBlockAt(location);
		return (block.getType() == material);
	}

	public static Component formatNumber(boolean colored, double ping, double target, double worst){
		String format = decimalFormat0.format(ping);
		if (colored){
			if (ping>=target)
				return Component.text(format, NamedTextColor.GREEN);
			else if (ping>= worst)
				return Component.text(format, NamedTextColor.YELLOW);
			else
				return Component.text(format, NamedTextColor.RED);
		} else
			return Component.text(format);
	}

	public static BlockFace direction(float yaw){
		switch (Math.round(yaw/45f) & 0x7) {
			default ->{
				return BlockFace.NORTH;
			}
			case 1 -> {
				return BlockFace.NORTH_EAST;
			}
			case 2 -> {
				return BlockFace.EAST;
			}
			case 3 -> {
				return BlockFace.SOUTH_EAST;
			}
			case 4 ->{
				return BlockFace.SOUTH;
			}
			case 5 -> {
				return BlockFace.SOUTH_WEST;
			}
			case 6 -> {
				return BlockFace.WEST;
			}
			case 7 -> {
				return BlockFace.NORTH_WEST;
			}
		}
	}
	public static String direction(boolean shortened, BlockFace blockFace){
		switch (blockFace){
			case NORTH -> {
				return shortened ? "N" : "North";
			}
			case NORTH_WEST -> {
				return shortened ? "NW" : "North West";
			}
			case NORTH_EAST -> {
				return shortened ? "NE" : "North East";
			}
			case EAST -> {
				return shortened ? "E" : "East";
			}
			case SOUTH_EAST -> {
				return shortened ? "SE" : "South East";
			}
			case SOUTH -> {
				return shortened ? "S" : "South";
			}
			case SOUTH_WEST -> {
				return shortened ? "SW" : "South West";
			}
			case WEST -> {
				return shortened ? "W" : "West";
			}
		}
		return "N";
	}
	public static boolean isAirOrNull(@Nullable ItemStack itemStack){
		return itemStack == null || itemStack.isEmpty();
	}

	public static boolean hasEmptySlot(EntityEquipment entityEquipment){
		if (isAirOrNull(entityEquipment.getHelmet()))
			return true;
		if (isAirOrNull(entityEquipment.getChestplate()))
			return true;
		if (isAirOrNull(entityEquipment.getBoots()))
			return true;
		if (isAirOrNull(entityEquipment.getLeggings()))
			return true;
		if (isAirOrNull(entityEquipment.getItemInMainHand()))
			return true;
		return isAirOrNull(entityEquipment.getItemInOffHand());
	}
	public static boolean hasEmptySlot(PlayerInventory playerInventory){
		if (isAirOrNull(playerInventory.getHelmet()))
			return true;
		if (isAirOrNull(playerInventory.getChestplate()))
			return true;
		if (isAirOrNull(playerInventory.getBoots()))
			return true;
		if (isAirOrNull(playerInventory.getLeggings()))
			return true;
		if (isAirOrNull(playerInventory.getItemInMainHand()))
			return true;
		if (isAirOrNull(playerInventory.getItemInOffHand()))
			return true;
		return playerInventory.contains(Material.AIR);
	}

	public static int emptySlots(EntityEquipment inv){
		int slots = 0;
		for (ItemStack armor : inv.getArmorContents()){
			if (isAirOrNull(armor))
				slots++;
		}
		if (isAirOrNull(inv.getItemInMainHand()))
			slots++;
		if (isAirOrNull(inv.getItemInOffHand()))
			slots++;
		return slots;
	}
	public static int emptySlots(PlayerInventory inv){
		int slots = 0;
		for (ItemStack armor : inv.getArmorContents()){
			if (isAirOrNull(armor))
				slots++;
		}
		for (ItemStack item : inv.getContents()) {
			if (isAirOrNull(item))
				slots++;
		}
		if (isAirOrNull(inv.getItemInMainHand()))
			slots++;
		if (isAirOrNull(inv.getItemInOffHand()))
			slots++;
		return slots;
	}


}
