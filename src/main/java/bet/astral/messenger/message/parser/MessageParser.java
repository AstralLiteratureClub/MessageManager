package bet.astral.messenger.message.parser;

import bet.astral.messenger.message.message.IMessage;
import bet.astral.messenger.message.part.IMessagePart;
import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.placeholder.Placeholder;
import bet.astral.messenger.utils.PlaceholderUtils;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageParser<M extends IMessage<? extends IMessagePart<C>, C>,  C> {
	private Map<String, Placeholder> globalPlaceholders;
	@Getter
	private final Logger logger;

	public MessageParser(Logger logger) {
		this.globalPlaceholders = new HashMap<>();
		this.logger = logger;
	}

	public void globalPlaceholders(@NotNull Map<String, Placeholder> placeholderMap) {
		this.globalPlaceholders = new HashMap<>();
	}
	public void globalPlaceholder(@NotNull Placeholder placeholder){
		this.globalPlaceholders.put(placeholder.key(), placeholder);
	}
	@Nullable
	public Placeholder globalPlaceholder(@NotNull String name){
		return this.globalPlaceholders.get(name);
	}
	@NotNull
	public Map<String, Placeholder> globalPlaceholders(){
		return this.globalPlaceholders;
	}
	public Map<MessageType, C> parse(@NotNull M message, @Nullable List<Placeholder> placeholders){
		Map<String, Placeholder> combinedPlaceholders = new HashMap<>(globalPlaceholders);
		combinedPlaceholders.putAll(message.placeholders());
		if (placeholders != null){
			combinedPlaceholders.putAll(PlaceholderUtils.asMap(placeholders));
		}

	}
	public Map<MessageType, C> parse(@NotNull M message, @NotNull Placeholder... placeholders){
		return parse(message, List.of(placeholders));
	}

	public C parse(@NotNull M message, @NotNull MessageType type, @Nullable List<Placeholder> placeholders){
	}
	public C parse(@NotNull M message, @NotNull MessageType type, @Nullable Placeholder[] placeholders){
	}


}
