package bet.astral.messenger;

import bet.astral.messenger.cloud.AbstractCommandMessenger;
import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.message.Message;
import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.message.serializer.DefaultMessageSerializer;
import bet.astral.messenger.message.serializer.IMessageSerializer;
import bet.astral.messenger.message.serializer.IMessageTypeSerializer;
import bet.astral.messenger.placeholder.Placeholder;
import bet.astral.messenger.placeholder.PlaceholderManager;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.configuration.MemorySection;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;


@Getter
public abstract class AbstractMessenger<P, Comp, CommandSender> extends AbstractCommandMessenger<Comp, CommandSender> {
	protected final P main;
	@Setter
	protected Logger logger;
	protected Map<String, IMessage<IMessagePart<Comp>, Comp>> messages;
	protected Map<String, IMessageSerializer<?, ?, Comp>> deserializers = new HashMap<>();
	protected Set<IMessageSerializer<?, ?, Comp>> titleDeserializers;
	@Setter
	private PlaceholderManager placeholderManager;
	@Getter
	@Setter
	@NotNull
	private IMessageTypeSerializer<Comp> messageTypeSerializer;
	@Getter
	@Setter
	private boolean consoleLogger = false;
	public final Class<Comp> componentType;

	public AbstractMessenger(CommandManager<CommandSender> commandManager, P main, Map<String, IMessage<IMessagePart<Comp>, Comp>> messages, @NotNull IMessageTypeSerializer<Comp> messageTypeSerializer, Class<Comp> componentType) {
		super(commandManager);
		this.main = main;
		this.messages = messages;
		this.messageTypeSerializer = messageTypeSerializer;
		this.titleDeserializers = new HashSet<>();
		this.componentType = componentType;
		deserializers.put("default", new DefaultMessageSerializer<>(messageTypeSerializer, Message.class));
	}

	@Nullable
	public abstract IMessage<?, Comp> loadMessage(@NotNull String messageKey);
	@NotNull
	public Map<String, Placeholder> loadPlaceholder(@NotNull String key, @NotNull MemorySection configuration) {
		return getPlaceholderManager().loadPlaceholders(key, configuration);
	}

	@Nullable
	public IMessage<?, Comp> getMessage(@NotNull String messageKey) {
		return messages.get(messageKey);
	}
	@NotNull
	public CompletableFuture<IMessage<?, Comp>> getMessageOrLoad(@NotNull String messageKey) {
		return CompletableFuture.supplyAsync(()->{
			IMessage<?, Comp> message = messages.get(messageKey);
			if (message == null){
				return loadMessage(messageKey);
			}
			return message;
		});
	}

	@Nullable
	public abstract Component parse(@NotNull IMessage<?, Component> message, @NotNull MessageType type, List<Placeholder> placeholders);

	public void addTitleSerializer(IMessageSerializer<?, ?, Comp> serializer){
		this.titleDeserializers.add(serializer);
	}

	public void removeTitleSerializer(IMessageSerializer<?, ?, Comp> serializer){
		this.titleDeserializers.remove(serializer);
	}


	public void broadcast(String messageKey, Placeholder... placeholders){
		broadcast(null, messageKey, 0, false, List.of(placeholders));
	}
	public void broadcast(Translatable translatable, Placeholder... placeholders){
		broadcast(null, translatable, 0, false, List.of(placeholders));
	}
	public void broadcast(String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(null, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(Translatable translatable, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(null, translatable, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(String messageKey, List<Placeholder> placeholders) {
		broadcast(null, messageKey, 0, false, placeholders);
	}
	public void broadcast(Translatable translatable, List<Placeholder> placeholders) {
		broadcast(null, translatable, 0, false, placeholders);
	}
	public void broadcast(String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(null, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Translatable translatable, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(null, translatable, 0, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Permission permission, String messageKey, Placeholder... placeholders){
		broadcast(permission, messageKey, 0, false, List.of(placeholders));
	}
	public void broadcast(Permission permission, Translatable translatable, Placeholder... placeholders){
		broadcast(permission, translatable, 0, false, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(permission, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(Permission permission, Translatable translatable, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(permission, translatable, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, 0, false, placeholders);
	}
	public void broadcast(Permission permission, Translatable translatable, List<Placeholder> placeholders) {
		broadcast(permission, translatable, 0, false, placeholders);
	}
	public void broadcast(Permission permission, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Permission permission, Translatable translatable, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(permission, translatable, 0, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(String messageKey, int delay, List<Placeholder> placeholders){
		broadcast(null, messageKey, delay, false, placeholders);
	}
	public void broadcast(Translatable translatable, int delay, List<Placeholder> placeholders){
		broadcast(null, translatable, delay, false, placeholders);
	}
	public void broadcast(String messageKey, int delay, Placeholder... placeholders){
		broadcast(null, messageKey, delay, false, List.of(placeholders));
	}
	public void broadcast(Translatable translatable, int delay, Placeholder... placeholders){
		broadcast(null, translatable, delay, false, List.of(placeholders));
	}
	public void broadcast(String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(null, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(Translatable translatable, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(null, translatable, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		broadcast(null, messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Translatable translatable, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		broadcast(null, translatable, delay, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Permission permission, String messageKey, int delay, Placeholder... placeholders){
		broadcast(permission, messageKey, delay, false, List.of(placeholders));
	}
	public void broadcast(Permission permission, Translatable translatable, int delay, Placeholder... placeholders){
		broadcast(permission, translatable, delay, false, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, int delay, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, delay, false, placeholders);
	}
	public void broadcast(Permission permission, Translatable translatable, int delay, List<Placeholder> placeholders) {
		broadcast(permission, translatable, delay, false, placeholders);
	}
	public void broadcast(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(permission, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(Permission permission, Translatable translatable, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(permission, translatable, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public abstract void broadcast(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders);
	public void broadcast(Permission permission, Translatable translatable, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(permission, translatable.translationKey(), delay, senderSpecificPlaceholders, placeholders);
	}
	public void message(Audience to, String messageKey, Placeholder... placeholders){
		message(null, to, messageKey, 0, false, List.of(placeholders));
	}
	public void message(Audience to, Translatable translatable, Placeholder... placeholders){
		message(null, to, translatable, 0, false, List.of(placeholders));
	}
	public void message(Audience to, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(null, to, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Audience to, Translatable translatable, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(null, to, translatable, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Audience to, String messageKey, List<Placeholder> placeholders) {
		message(null, to, messageKey, 0, false, placeholders);
	}
	public void message(Audience to, Translatable translatable, List<Placeholder> placeholders) {
		message(null, to, translatable, 0, false, placeholders);
	}
	public void message(Audience to, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(null, to, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void message(Audience to, Translatable translatable, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(null, to, translatable, 0, senderSpecificPlaceholders, placeholders);
	}
	public void message(Permission permission, Audience to, String messageKey, Placeholder... placeholders){
		message(permission, to, messageKey, 0, false, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, Translatable translatable, Placeholder... placeholders){
		message(permission, to, translatable, 0, false, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(permission, to, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, Translatable translatable, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(permission, to, translatable, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, String messageKey, List<Placeholder> placeholders) {
		message(permission, to, messageKey, 0, false, placeholders);
	}
	public void message(Permission permission, Audience to, Translatable translatable, List<Placeholder> placeholders) {
		message(permission, to, translatable, 0, false, placeholders);
	}
	public void message(Permission permission, Audience to, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(permission, to, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void message(Permission permission, Audience to, Translatable translatable, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(permission, to, translatable, 0, senderSpecificPlaceholders, placeholders);
	}
	public void message(Audience to, String messageKey, int delay, List<Placeholder> placeholders){
		message(null, to, messageKey, delay, false, placeholders);
	}
	public void message(Audience to, Translatable translatable, int delay, List<Placeholder> placeholders){
		message(null, to, translatable, delay, false, placeholders);
	}
	public void message(Audience to, String messageKey, int delay, Placeholder... placeholders){
		message(null, to, messageKey, delay, false, List.of(placeholders));
	}
	public void message(Audience to, Translatable translatable, int delay, Placeholder... placeholders){
		message(null, to, translatable, delay, false, List.of(placeholders));
	}
	public void message(Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(null, to, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Audience to, Translatable translatable, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(null, to, translatable, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		message(null, to, messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	public void message(Audience to, Translatable translatable, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		message(null, to, translatable, delay, senderSpecificPlaceholders, placeholders);
	}
	public void message(Permission permission, Audience to, String messageKey, int delay, Placeholder... placeholders){
		message(permission, to, messageKey, delay, false, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, Translatable translatable, int delay, Placeholder... placeholders){
		message(permission, to, translatable, delay, false, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, String messageKey, int delay, List<Placeholder> placeholders) {
		message(permission, to, messageKey, delay, false, placeholders);
	}
	public void message(Permission permission, Audience to, Translatable translatable, int delay, List<Placeholder> placeholders) {
		message(permission, to, translatable, delay, false, placeholders);
	}
	public void message(Permission permission, Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(permission, to, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, Translatable translatable, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(permission, to, translatable, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public abstract void message(Permission permission, Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders);
	public void message(Permission permission, Audience to, Translatable translatable, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(permission, to, translatable.translationKey(), delay, senderSpecificPlaceholders, placeholders);
	}
}
