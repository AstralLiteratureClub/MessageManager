package bet.astral.messenger.v2.bukkit.scheduler;

import bet.astral.messenger.v2.bukkit.BukkitMessenger;
import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.task.IDelayedTask;
import bet.astral.messenger.v2.task.IScheduler;
import bet.astral.messenger.v2.task.ITask;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GlobalASyncScheduler implements IScheduler {
	public static GlobalASyncScheduler SCHEDULER = new GlobalASyncScheduler();
	@Override
	public void run(@NotNull Consumer<@NotNull ITask> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(BukkitMessenger.PLUGIN, task->consumer.accept(new BukkitTask(task, this)));
	}

	@Override
	public void runLater(@NotNull Consumer<@NotNull IDelayedTask> consumer, @NotNull Delay delay) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMessenger.PLUGIN, task->consumer.accept(new BukkitTask(task, this)), delay.toTicks());
	}
}
