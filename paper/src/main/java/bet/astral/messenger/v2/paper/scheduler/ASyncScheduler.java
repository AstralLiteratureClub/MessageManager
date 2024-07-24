package bet.astral.messenger.v2.paper.scheduler;

import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.paper.PaperMessenger;
import bet.astral.messenger.v2.task.IDelayedTask;
import bet.astral.messenger.v2.task.IScheduler;
import bet.astral.messenger.v2.task.ITask;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ASyncScheduler implements IScheduler {
	public static ASyncScheduler SCHEDULER = new ASyncScheduler();
	@Override
	public ITask run(@NotNull Consumer<@NotNull ITask> consumer) {
		AtomicReference<PaperTask> taskReference = new AtomicReference<>();
		Consumer<ScheduledTask> taskConsumer = (task)->{
			consumer.accept(taskReference.get());
			if (taskReference.get().getRunAfter() != null){
				for (Consumer<ITask> newTask : Objects.requireNonNull(taskReference.get().getRunAfter())){
					run(newTask);
				}
			}
			if (taskReference.get().getRunAfterLater() != null){
				for (Pair<Consumer<IDelayedTask>, Delay> pair : Objects.requireNonNull(taskReference.get().getRunAfterLater())){
					runLater(pair.getLeft(), pair.getRight());
				}
			}
		};
		taskReference.set(new PaperTask(Bukkit.getAsyncScheduler().runNow(PaperMessenger.PLUGIN, taskConsumer), this));
		return taskReference.get();
	}

	@Override
	public IDelayedTask runLater(@NotNull Consumer<@NotNull IDelayedTask> consumer, @NotNull Delay delay) {
		AtomicReference<PaperTask> taskReference = new AtomicReference<>();
		Consumer<ScheduledTask> taskConsumer = (task)->{
			consumer.accept(taskReference.get());
			if (taskReference.get().getRunAfter() != null){
				for (Consumer<ITask> newTask : Objects.requireNonNull(taskReference.get().getRunAfter())){
					run(newTask);
				}
			}
			if (taskReference.get().getRunAfterLater() != null){
				for (Pair<Consumer<IDelayedTask>, Delay> pair : Objects.requireNonNull(taskReference.get().getRunAfterLater())){
					runLater(pair.getLeft(), pair.getRight());
				}
			}
		};
		taskReference.set(new PaperTask(Bukkit.getAsyncScheduler().runDelayed(PaperMessenger.PLUGIN, taskConsumer, delay.delay(), delay.timeUnit()), this, delay));
		return taskReference.get();
	}
}
