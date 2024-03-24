package bet.astral.messenger;

import bet.astral.messenger.message.part.IMessagePart;

@FunctionalInterface
public interface AudienceForwarder<Audience, Component> {
	void forward(Audience audience, IMessagePart<Component> messagePart, Component component);
}
