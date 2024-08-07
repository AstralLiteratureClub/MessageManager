package bet.astral.messenger.v2.cloud.paper.locale;

import org.bukkit.entity.Player;

import java.util.Locale;

public class CommandSenderLocaleExtractor extends bet.astral.messenger.v2.cloud.bukkit.locale.CommandSenderLocaleExtractor {
	@Override
	public @org.checkerframework.checker.nullness.qual.NonNull Locale extract(org.bukkit.command.@org.jetbrains.annotations.NotNull CommandSender recipient) {
		return recipient instanceof Player player ? player.locale() : Locale.US;
	}
}
