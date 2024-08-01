package bet.astral.messenger.v2.receiver;

import bet.astral.messenger.v2.DefaultScheduler;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.task.IScheduler;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class AudienceReceiver implements Receiver, Audience {
	private final Collection<Audience> audience;

	public AudienceReceiver(Collection<Audience> audience) {
		this.audience = audience;
	}
	public AudienceReceiver(Audience... audience) {
		this.audience = List.of(audience);
	}

	@Override
	public @NotNull IScheduler getScheduler() {
		return DefaultScheduler.ASYNC_SCHEDULER;
	}

	@Override
	public @NotNull Locale getLocale() {
		return Locale.US;
	}

	@Override
	public boolean hasPermission(@NotNull Permission permission) {
		return permission.test(this);
	}

	@Override
	public boolean hasPermission(@NotNull String permission) {
		return false;
	}

	@Override
	public @NotNull Iterable<? extends Audience> audiences() {
		return audience;
	}
}
