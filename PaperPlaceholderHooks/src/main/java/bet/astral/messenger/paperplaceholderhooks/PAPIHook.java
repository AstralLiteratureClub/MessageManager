package bet.astral.messenger.paperplaceholderhooks;

import bet.astral.messenger.v2.placeholder.hooks.PlaceholderHook;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import bet.astral.platform.entity.player.IOfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PAPIHook implements PlaceholderHook {
	private final PlaceholderExpansion placeholderExpansion;

	public PAPIHook(PlaceholderExpansion placeholderExpansion) {
		this.placeholderExpansion = placeholderExpansion;
	}

	@Override
	public @NotNull String getName() {
		return placeholderExpansion.getName();
	}

	@Override
	public @NotNull String getAuthor() {
		return placeholderExpansion.getAuthor();
	}

	@Override
	public @NotNull String getWebsite() {
		return placeholderExpansion.getPlaceholderAPI().getPluginMeta().getWebsite() != null ? placeholderExpansion.getPlaceholderAPI().getPluginMeta().getWebsite() : "";
	}

	@Override
	public @NotNull String getVersion() {
		return placeholderExpansion.getVersion();
	}

	@Override
	public @Nullable PlaceholderValue handle(@NotNull IOfflinePlayer<?, ?> iOfflinePlayer, String s) {
		if (iOfflinePlayer.getOfflinePlayerPlatform() instanceof Player) {
			String value = placeholderExpansion.onPlaceholderRequest((Player) iOfflinePlayer.getOfflinePlayerPlatform(), s);
			if (value != null) {
				return PlaceholderValue.legacy(value);
			}
		} else {
			String value = placeholderExpansion.onRequest((OfflinePlayer) iOfflinePlayer.getOfflinePlayerPlatform(), s);
			if (value != null) {
				return PlaceholderValue.legacy(value);
			}
		}
		return null;
	}
}
