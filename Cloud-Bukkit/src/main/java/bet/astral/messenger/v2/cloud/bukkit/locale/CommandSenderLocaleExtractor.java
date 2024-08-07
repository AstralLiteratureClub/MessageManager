package bet.astral.messenger.v2.cloud.bukkit.locale;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.translations.LocaleExtractor;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class CommandSenderLocaleExtractor implements LocaleExtractor<CommandSender> {
	@Override
	public @NonNull Locale extract(@NotNull CommandSender recipient) {
		if (recipient instanceof Player player){
			String[] split = player.getLocale().split("_");
			return new Locale(split[0], split[1]);
		}
		return Locale.US;
	}
}
