package bet.astral.messenger.v2.delay;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

record DelayImpl(long delay, @NotNull TimeUnit timeUnit) implements Delay {
}
