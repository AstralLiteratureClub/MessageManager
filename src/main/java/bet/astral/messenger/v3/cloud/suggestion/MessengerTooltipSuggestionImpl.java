package bet.astral.messenger.v3.cloud.suggestion;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.info.MessageInfo;
import bet.astral.messenger.v2.receiver.Receiver;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

record MessengerTooltipSuggestionImpl(Messenger messenger, MessageInfo messageInfo,
                                             String suggestion) implements MessengerTooltipSuggestion {
	public MessengerTooltipSuggestionImpl(@NotNull Messenger messenger, @NotNull MessageInfo messageInfo, @NotNull String suggestion) {
		this.messenger = messenger;
		this.messageInfo = messageInfo;
		this.suggestion = suggestion;
	}

	@Override
	public @Nullable Component tooltip() {
		if (messageInfo.getReceivers().isEmpty()) {
			return messageInfo.parseAsComponent(messenger, ComponentType.CHAT);
		} else {
			Object receiverObj = List.copyOf(messageInfo.getReceivers()).get(0);
			Receiver receiver = messenger.convertReceiver(receiverObj);
			if (receiver == null){
				return messageInfo.parseAsComponent(messenger, ComponentType.CHAT);
			}
			return messenger.parseComponent(messageInfo, ComponentType.CHAT, receiver);
		}
	}

	@Override
	public @NotNull Messenger getMessenger() {
		return messenger;
	}

	@Override
	public @NotNull MessageInfo getMessageInfo() {
		return messageInfo;
	}

	@Override
	public @NonNull MessengerTooltipSuggestionImpl withSuggestion(@NonNull String suggestion) {
		return (MessengerTooltipSuggestionImpl) MessengerTooltipSuggestion.of(suggestion, messenger, messageInfo);
	}
}
