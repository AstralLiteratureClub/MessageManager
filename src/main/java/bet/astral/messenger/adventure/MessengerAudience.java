package bet.astral.messenger.adventure;

import bet.astral.messenger.Messenger;
import bet.astral.messenger.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.permission.Permission;

import java.util.List;

public interface MessengerAudience<P extends JavaPlugin> extends ForwardingAudience {
	Messenger<P, Component, Audience> messenger();

	default List<Placeholder> createPlaceholders(){
		return messenger().getPlaceholderManager().placeholders(this);
	}

	default void message(String  messageKey, Placeholder... placeholders){
		messenger().message(this, messageKey, 0, false, List.of(placeholders));
	}
	default void message(String  messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(this, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(String messageKey, List<Placeholder> placeholders) {
		messenger().message(this, messageKey, 0, false, placeholders);
	}
	default void message(String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(this, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	default void message(Permission permission, String messageKey, Placeholder... placeholders){
		messenger().message(permission, this, messageKey, 0, false, List.of(placeholders));
	}
	default void message(Permission permission, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(permission, this, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(Permission permission, String messageKey, List<Placeholder> placeholders) {
		messenger().message(permission, this, messageKey, 0, false, placeholders);
	}
	default void message(Permission permission, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(permission, this, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	default void message(String messageKey, int delay, List<Placeholder> placeholders){
		messenger().message(this, messageKey, delay, false, placeholders);
	}
	default void message(String messageKey, int delay, Placeholder... placeholders){
		messenger().message(this, messageKey, delay, false, List.of(placeholders));
	}
	default void message(String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(this, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		messenger().message(this, messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	default void message(Permission permission, String  messageKey, int delay, Placeholder... placeholders){
		messenger().message(permission, this, messageKey, delay, false, List.of(placeholders));
	}
	default void message(Permission permission, String messageKey, int delay, List<Placeholder> placeholders) {
		messenger().message(permission, this, messageKey, delay, false, placeholders);
	}
	default void message(Permission permission, String  messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(permission, this, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(permission, this, messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
}
