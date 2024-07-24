package bet.astral.messenger.v2.task;

import bet.astral.messenger.v2.delay.Delay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ITask {
	void cancel();
	boolean isCanceled();
	boolean isASync();
	int getId();
	IScheduler getTaskProvider();

	void runAfter(@NotNull Consumer<@NotNull ITask> taskConsumer);
	void runLaterAfter(@NotNull Consumer<@NotNull IDelayedTask> taskConsumer, @NotNull Delay delay);
}
