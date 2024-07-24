package bet.astral.messenger.v2.task;

import bet.astral.messenger.v2.delay.Delay;
import org.jetbrains.annotations.NotNull;

public interface IDelayedTask extends ITask {
	@NotNull
	Delay getDelay();
}
