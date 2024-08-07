package bet.astral.messenger.v2.paper.scheduler;

import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.paper.PaperMessenger;
import bet.astral.messenger.v2.task.IDelayedTask;
import bet.astral.messenger.v2.task.IScheduler;
import bet.astral.messenger.v2.task.ITask;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class EntityScheduler implements IScheduler {
	private final Entity entity;

	public EntityScheduler(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void run(@NotNull Consumer<@NotNull ITask> consumer) {
		entity.getScheduler().run(PaperMessenger.PLUGIN, t->consumer.accept(new PaperTask(t, this)), null);
	}

	@Override
	public void runLater(@NotNull Consumer<@NotNull IDelayedTask> consumer, @NotNull Delay delay) {
		entity.getScheduler().runDelayed(PaperMessenger.PLUGIN, t->consumer.accept(new PaperTask(t, this)), null, delay.toTicks());
	}
}
