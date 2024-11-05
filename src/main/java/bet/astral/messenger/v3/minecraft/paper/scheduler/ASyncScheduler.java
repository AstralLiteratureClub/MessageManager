package bet.astral.messenger.v3.minecraft.paper.scheduler;

import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v3.minecraft.paper.PaperMessenger;
import bet.astral.messenger.v2.task.IDelayedTask;
import bet.astral.messenger.v2.task.IScheduler;
import bet.astral.messenger.v2.task.ITask;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ASyncScheduler implements IScheduler {
	public static ASyncScheduler SCHEDULER = new ASyncScheduler();
	@Override
	public void run(@NotNull Consumer<@NotNull ITask> consumer) {
		Bukkit.getAsyncScheduler().runNow(PaperMessenger.PLUGIN, t->consumer.accept(new PaperTask(t, this)));
	}

	@Override
	public void runLater(@NotNull Consumer<@NotNull IDelayedTask> consumer, @NotNull Delay delay) {
		Bukkit.getAsyncScheduler().runDelayed(PaperMessenger.PLUGIN, t->consumer.accept(new PaperTask(t, this)), delay.delay(), delay.timeUnit());
	}
}
