package bet.astral.messenger.v2.receiver;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.task.IScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Locale;

public class ForwardingReceiverImpl implements ForwardingReceiver{
	private final Collection<Receiver> receivers;

	public ForwardingReceiverImpl(Collection<Receiver> receivers) {
		this.receivers = receivers;
	}

	@Override
	public @NotNull Collection<? extends Receiver> getReceivers() {
		return receivers;
	}

	@Override
	public @NotNull IScheduler getScheduler() {
		return Messenger.getScheduler();
	}

	@Override
	public @NotNull Locale getLocale() {
		return Locale.US;
	}

	@Override
	public boolean hasPermission(@NotNull Permission<?> permission) {
		return false;
	}

	@Override
	public boolean hasPermission(@NotNull String s) {
		return false;
	}
}
