package bet.astral.messenger.offline;

import net.kyori.adventure.text.Component;

public record ParsedOfflineMessage(String key, Component chat, Component action_bar, Component title, Component subtitle, long expiryDate, boolean expires) {
}
