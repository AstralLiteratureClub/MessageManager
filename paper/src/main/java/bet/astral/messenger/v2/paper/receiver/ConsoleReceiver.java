package bet.astral.messenger.v2.paper.receiver;

import bet.astral.messenger.v2.paper.scheduler.ASyncScheduler;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.task.IScheduler;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ConsoleReceiver implements Receiver {
	public static final ConsoleReceiver CONSOLE_RECEIVER = new ConsoleReceiver();
	@Override
	public @NotNull IScheduler getScheduler() {
		return ASyncScheduler.SCHEDULER;
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
	public boolean hasPermission(@NotNull String s) {
		return Bukkit.getConsoleSender().hasPermission(s);
	}

	@SuppressWarnings("UnstableApiUsage")
	public void sendMessage(final @NotNull Identity source, final @NotNull Component message, final @NotNull MessageType type) {
		CONSOLE_RECEIVER.sendMessage(source, message, type);
	}
}
