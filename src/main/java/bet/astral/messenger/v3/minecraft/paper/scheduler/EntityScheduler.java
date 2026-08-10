package bet.astral.messenger.v3.minecraft.paper.scheduler;

import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.task.ITask;
import bet.astral.messenger.v3.minecraft.paper.PaperMessenger;
import bet.astral.messenger.v2.task.IDelayedTask;
import bet.astral.messenger.v2.task.IScheduler;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

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
	public IDelayedTask runLater(@NotNull Consumer<@NotNull IDelayedTask> consumer, @NotNull Delay delay) {
		PaperTask task = new PaperTask(null, this);
		entity.getScheduler().runDelayed(PaperMessenger.PLUGIN, paperTask-> {
			if (task.isCanceled()) {
				return;
			}
			task.update(paperTask);
			consumer.accept(task);
		}, null, delay.toTicks());

		return task;

	}
}
