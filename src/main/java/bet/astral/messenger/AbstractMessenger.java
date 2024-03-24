package bet.astral.messenger;

import bet.astral.messenger.cloud.AbstractCommandMessenger;
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
import org.apache.logging.log4j.Logger;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class AbstractMessenger<P, Comp, Audience> extends AbstractCommandMessenger<Comp, Audience> {
	protected final P main;
	@Setter
	protected Logger logger;
	protected Map<String, IMessage<IMessagePart<Comp>, Comp>> messages;
	protected Map<String, IMessageSerializer<?, ?, Comp>> deserializers = new HashMap<>();
	@Setter
	private PlaceholderManager placeholderManager;
	@Getter
	@Setter
	@NotNull
	private IMessageTypeSerializer<Comp> messageTypeSerializer;
	@Getter
	@Setter
	private boolean consoleLogger = false;
	private final AudienceForwarder<Audience, Comp> forwarder;
	public final Class<Comp> componentType;

	public AbstractMessenger(CommandManager<Audience> commandManager, P main, Map<String, IMessage<IMessagePart<Comp>, Comp>> messages, @NotNull IMessageTypeSerializer<Comp> messageTypeSerializer, AudienceForwarder<Audience, Comp> forwarder, Class<Comp> componentType) {
		super(commandManager);
		this.main = main;
		this.messages = messages;
		this.messageTypeSerializer = messageTypeSerializer;
		this.forwarder = forwarder;
		this.componentType = componentType;
		deserializers.put("default", new DefaultMessageSerializer<>(messageTypeSerializer, Message.class));
	}

	public void broadcast(String  messageKey, Placeholder... placeholders){
		broadcast(null, messageKey, 0, false, List.of(placeholders));
	}
	public void broadcast(String  messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(null, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(String messageKey, List<Placeholder> placeholders) {
		broadcast(null, messageKey, 0, false, placeholders);
	}
	public void broadcast(String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(null, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Permission permission, String messageKey, Placeholder... placeholders){
		broadcast(permission, messageKey, 0, false, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(permission, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, 0, false, placeholders);
	}
	public void broadcast(Permission permission, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(String messageKey, int delay, List<Placeholder> placeholders){
		broadcast(null, messageKey, delay, false, placeholders);
	}
	public void broadcast(String messageKey, int delay, Placeholder... placeholders){
		broadcast(null, messageKey, delay, false, List.of(placeholders));
	}
	public void broadcast(String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(null, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void broadcast(String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		broadcast(null, messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	public void broadcast(Permission permission, String  messageKey, int delay, Placeholder... placeholders){
		broadcast(permission, messageKey, delay, false, List.of(placeholders));
	}
	public void broadcast(Permission permission, String messageKey, int delay, List<Placeholder> placeholders) {
		broadcast(permission, messageKey, delay, false, placeholders);
	}
	public void broadcast(Permission permission, String  messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		broadcast(permission, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public abstract void broadcast(Permission permission, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders);
	public void message(Audience to, String  messageKey, Placeholder... placeholders){
		message(null, to, messageKey, 0, false, List.of(placeholders));
	}
	public void message(Audience to, String  messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(null, to, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Audience to, String messageKey, List<Placeholder> placeholders) {
		message(null, to, messageKey, 0, false, placeholders);
	}
	public void message(Audience to, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(null, to, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void message(Permission permission, Audience to, String messageKey, Placeholder... placeholders){
		message(permission, to, messageKey, 0, false, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, String messageKey, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(permission, to, messageKey, 0, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, String messageKey, List<Placeholder> placeholders) {
		message(permission, to, messageKey, 0, false, placeholders);
	}
	public void message(Permission permission, Audience to, String messageKey, boolean senderSpecificPlaceholders, List<Placeholder> placeholders) {
		message(permission, to, messageKey, 0, senderSpecificPlaceholders, placeholders);
	}
	public void message(Audience to, String messageKey, int delay, List<Placeholder> placeholders){
		message(null, to, messageKey, delay, false, placeholders);
	}
	public void message(Audience to, String messageKey, int delay, Placeholder... placeholders){
		message(null, to, messageKey, delay, false, List.of(placeholders));
	}
	public void message(Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(null, to, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public void message(Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders){
		message(null, to, messageKey, delay, senderSpecificPlaceholders, placeholders);
	}
	public void message(Permission permission, Audience to, String  messageKey, int delay, Placeholder... placeholders){
		message(permission, to, messageKey, delay, false, List.of(placeholders));
	}
	public void message(Permission permission, Audience to, String messageKey, int delay, List<Placeholder> placeholders) {
		message(permission, to, messageKey, delay, false, placeholders);
	}
	public void message(Permission permission, Audience to, String  messageKey, int delay, boolean senderSpecificPlaceholders, Placeholder... placeholders){
		message(permission, to, messageKey, delay, senderSpecificPlaceholders, List.of(placeholders));
	}
	public abstract void message(Permission permission, Audience to, String messageKey, int delay, boolean senderSpecificPlaceholders, List<Placeholder> placeholders);
}
