package bet.astral.messenger.v2.receiver;

import java.util.function.Function;

/**
 * Converts objects to receivers which the messenger v2 can use to send messages
 */
@FunctionalInterface
public interface ReceiverConverter extends Function<Object, Receiver> { }
