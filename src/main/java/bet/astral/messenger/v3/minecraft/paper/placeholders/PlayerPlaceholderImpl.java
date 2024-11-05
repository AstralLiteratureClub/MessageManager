package bet.astral.messenger.v3.minecraft.paper.placeholders;

import bet.astral.messenger.v2.placeholder.AbstractPlaceholder;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.values.PlaceholderValue;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

class PlayerPlaceholderImpl extends AbstractPlaceholder implements PlayerPlaceholder{
	private final OfflinePlayer player;
	private final Component value;

	protected PlayerPlaceholderImpl(String key, OfflinePlayer player, Component value) {
		super(key);
		this.player = player;
		this.value = value;
	}

	@Override
	public @NotNull OfflinePlayer getPlayer() {
		return player;
	}

	@Override
	public @NotNull Placeholder clone(@NotNull String s) {
		return new PlayerPlaceholderImpl(s, player, value);
	}

	@Override
	public @NotNull PlaceholderValue getPlaceholderValue() {
		return this;
	}

	@Override
	public @NotNull Component getValue() {
		return value;
	}
}
