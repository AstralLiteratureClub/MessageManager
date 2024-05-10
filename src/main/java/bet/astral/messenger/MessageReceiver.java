package bet.astral.messenger;

import bet.astral.messenger.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.permission.Permission;

import java.util.List;

public interface MessageReceiver<P extends JavaPlugin> {
	Messenger<P> messenger();
	Audience getAudience();

	default void message(String messageKey, Placeholder... placeholders){
		messenger().message(getAudience(), messageKey, 0, false, List.of(placeholders));
	}
	default void message(Translatable translatable, Placeholder... placeholders){
		messenger().message(getAudience(), translatable, 0, false, List.of(placeholders));
	}
	default void message(String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(getAudience(), messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(Translatable translatable, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(getAudience(), translatable, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(String messageKey, List<Placeholder> placeholders) {
		messenger().message(getAudience(), messageKey, 0, false, placeholders);
	}
	default void message(Translatable translatable, List<Placeholder> placeholders) {
		messenger().message(getAudience(), translatable, 0, false, placeholders);
	}
	default void message(String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(getAudience(), messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	default void message(Translatable translatable, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(getAudience(), translatable, 0, senderSpecificPlaceholders, placeholders);
	}
	default void message(Permission permission, String messageKey, Placeholder... placeholders){
		messenger().message(permission, getAudience(), messageKey, 0, false, List.of(placeholders));
	}
	default void message(Permission permission, Translatable translatable, Placeholder... placeholders){
		messenger().message(permission, getAudience(), translatable, 0, false, List.of(placeholders));
	}
	default void message(Permission permission, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(permission, getAudience(), messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(Permission permission, Translatable translatable, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(permission, getAudience(), translatable, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(Permission permission, String messageKey, List<Placeholder> placeholders) {
		messenger().message(permission, getAudience(), messageKey, 0, false, placeholders);
	}
	default void message(Permission permission, Translatable translatable, List<Placeholder> placeholders) {
		messenger().message(permission, getAudience(), translatable, 0, false, placeholders);
	}
	default void message(Permission permission, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(permission, getAudience(), messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	default void message(Permission permission, Translatable translatable, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(permission, getAudience(), translatable, 0, senderSpecificPlaceholders, placeholders);
	}
	default void message(String messageKey, int delay, List<Placeholder> placeholders){
		messenger().message(getAudience(), messageKey, delay, false, placeholders);
	}
	default void message(Translatable translatable, int delay, List<Placeholder> placeholders){
		messenger().message(getAudience(), translatable, delay, false, placeholders);
	}
	default void message(String messageKey, int delay, Placeholder... placeholders){
		messenger().message(getAudience(), messageKey, delay, false, List.of(placeholders));
	}
	default void message(Translatable translatable, int delay, Placeholder... placeholders){
		messenger().message(getAudience(), translatable, delay, false, List.of(placeholders));
	}
	default void message(String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(getAudience(), messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(Translatable translatable, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(getAudience(), translatable, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		messenger().message(getAudience(), messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	default void message(Translatable translatable, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		messenger().message(getAudience(), translatable, delay, senderSpecificPlaceholders, placeholders);
	}
	default void message(Permission permission, String messageKey, int delay, Placeholder... placeholders){
		messenger().message(permission, getAudience(), messageKey, delay, false, List.of(placeholders));
	}
	default void message(Permission permission, Translatable translatable, int delay, Placeholder... placeholders){
		messenger().message(permission, getAudience(), translatable, delay, false, List.of(placeholders));
	}
	default void message(Permission permission, String messageKey, int delay, List<Placeholder> placeholders) {
		messenger().message(permission, getAudience(), messageKey, delay, false, placeholders);
	}
	default void message(Permission permission, Translatable translatable, int delay, List<Placeholder> placeholders) {
		messenger().message(permission, getAudience(), translatable, delay, false, placeholders);
	}
	default void message(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(permission, getAudience(), messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(Permission permission, Translatable translatable, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		messenger().message(permission, getAudience(), translatable, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	default void message(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(permission, getAudience(), messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	default void message(Permission permission, Translatable translatable, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		messenger().message(permission, getAudience(), translatable, delay, senderSpecificPlaceholders, placeholders);
	}
}
