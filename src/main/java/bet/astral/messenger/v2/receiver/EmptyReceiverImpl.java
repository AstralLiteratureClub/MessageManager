package bet.astral.messenger.v2.receiver;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.task.IScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

class EmptyReceiverImpl implements Receiver {
	public static EmptyReceiverImpl emptyReceiver = new EmptyReceiverImpl();
	@Override
	public boolean hasPermission(@NotNull Permission permission) {
		return false;
	}

	@Override
	public boolean hasPermission(@NotNull String permission) {
		return false;
	}

	@Override
	public @NotNull IScheduler getScheduler() {
		return Messenger.getScheduler();
	}

	@Override
	public @NotNull Locale getLocale() {
		return Locale.US;
	}
}
