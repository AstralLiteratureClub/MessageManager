package bet.astral.messenger.adventure;

import bet.astral.messenger.placeholder.Placeholder;
import bet.astral.messenger.placeholder.PlaceholderManager;
import bet.astral.messenger.placeholder.Placeholderable;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class AdventurePlaceholderManager extends PlaceholderManager {
	public List<Placeholder> audiencePlaceholders(@Nullable String prefix, @NotNull Audience audience){
		if (audience instanceof Player player){
			return playerPlaceholders(prefix, player);
		} else if (audience instanceof CommandSender sender){
			return senderPlaceholders(prefix, sender);
		} else if (audience instanceof MessengerAudience<?> messengerAudience) {
			return List.copyOf(messengerAudience.asPlaceholder(prefix));
		} else if (audience instanceof Placeholderable placeholderable){
			return List.copyOf(placeholderable.asPlaceholder(prefix));
		}
		return Collections.emptyList();
	}
}
