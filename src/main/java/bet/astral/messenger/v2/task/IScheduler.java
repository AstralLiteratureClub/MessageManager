package bet.astral.messenger.v2.task;

import bet.astral.messenger.v2.delay.Delay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface IScheduler {
	ITask run(@NotNull Consumer<@NotNull ITask> taskConsumer);

	IDelayedTask runLater(@NotNull Consumer<@NotNull IDelayedTask> taskConsumer, @NotNull Delay delay);
}