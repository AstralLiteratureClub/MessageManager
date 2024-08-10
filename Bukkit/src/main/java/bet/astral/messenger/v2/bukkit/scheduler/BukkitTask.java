package bet.astral.messenger.v2.bukkit.scheduler;

import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.task.IDelayedTask;
import bet.astral.messenger.v2.task.IScheduler;
import bet.astral.messenger.v2.task.ITask;
import bet.astral.more4j.tuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BukkitTask implements ITask, IDelayedTask {
	private static int LATEST_ID = 0;
	private final org.bukkit.scheduler.BukkitTask task;
	private final IScheduler scheduler;
	private final int currentId = LATEST_ID++;
	private List<Consumer<ITask>> runAfter;
	private List<Pair<Consumer<IDelayedTask>, Delay>> runAfterLater;
	private final Delay delay;

	public BukkitTask(org.bukkit.scheduler.BukkitTask task, IScheduler scheduler) {
		this.task = task;
		this.scheduler = scheduler;
		this.delay = null;
	}

	public BukkitTask(org.bukkit.scheduler.BukkitTask task, IScheduler scheduler, Delay delay) {
		this.task = task;
		this.scheduler = scheduler;
		this.delay = delay;
	}

	@Override
	public void cancel() {
		task.cancel();
	}

	@Override
	public boolean isCanceled() {
		return task.isCancelled();
	}

	@Override
	public boolean isASync() {
		return false;
	}

	@Override
	public int getId() {
		return currentId;
	}

	@Override
	public IScheduler getTaskProvider() {
		return scheduler;
	}

	@Override
	public void runAfter(@NotNull Consumer<@NotNull ITask> consumer) {
		if (runAfter == null) {
			runAfter = new LinkedList<>();
		}
		runAfter.add(consumer);
	}

	@Override
	public void runLaterAfter(@NotNull Consumer<@NotNull IDelayedTask> consumer, @NotNull Delay delay) {
		if (runAfterLater == null) {
			runAfterLater = new LinkedList<>();
		}
		runAfterLater.add(Pair.immutable(consumer, delay));
	}

	@Nullable
	public List<Consumer<ITask>> getRunAfter() {
		return runAfter;
	}

	@Nullable
	public List<Pair<Consumer<IDelayedTask>, Delay>> getRunAfterLater() {
		return runAfterLater;
	}

	@Override
	public @NotNull Delay getDelay() {
		return delay != null ? delay : Delay.NONE;
	}
}
