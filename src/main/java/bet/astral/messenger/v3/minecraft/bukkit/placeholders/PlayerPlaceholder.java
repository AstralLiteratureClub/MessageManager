package bet.astral.messenger.v3.minecraft.bukkit.placeholders;

import bet.astral.messenger.v2.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerPlaceholder extends Placeholder {
	/**
	 *
	 * @param name
	 * @param player
	 * @return
	 */
	static PlayerPlaceholder of(@NotNull String name, @NotNull OfflinePlayer player){
		return new PlayerPlaceholderImpl(name, player, Component.text(player.getName()));
	}

	/**
	 * Returns a new placeholder which uses the given player's health as the component value
	 * @param name name
	 * @param player player
	 * @return new placeholder
	 */
	static PlayerPlaceholder health(@NotNull String name, @NotNull Player player){
		return new PlayerPlaceholderImpl(name, player, Component.text(player.getHealth()));
	}

	/**
	 * Returns a new placeholder which uses given player's displayname as the component value
	 * @param name name
	 * @param player player
	 * @return new placeholder
	 */
	static PlayerPlaceholder displayname(@NotNull String name, @NotNull Player player){
		return new PlayerPlaceholderImpl(name, player, LegacyComponentSerializer.legacySection().deserialize(player.getDisplayName()));
	}

	/**
	 * Returns the player used within this placeholder
	 * @return player
	 */
	@NotNull
	OfflinePlayer getPlayer();
}
