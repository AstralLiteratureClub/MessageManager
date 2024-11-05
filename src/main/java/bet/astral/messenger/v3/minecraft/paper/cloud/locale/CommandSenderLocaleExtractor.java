package bet.astral.messenger.v3.minecraft.paper.cloud.locale;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.translations.LocaleExtractor;

import java.util.Locale;

public class CommandSenderLocaleExtractor implements LocaleExtractor<CommandSender> {
	@Override
	public @org.checkerframework.checker.nullness.qual.NonNull Locale extract(org.bukkit.command.@org.jetbrains.annotations.NotNull CommandSender recipient) {
		return recipient instanceof Player player ? player.locale() : Locale.US;
	}
}
