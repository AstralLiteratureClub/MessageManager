package bet.astral.messenger.v2;

import bet.astral.messenger.v2.receiver.Receiver;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

@Getter
public abstract class Platform {
	private static Platform platform = null;
	@NotNull
	private final String name;
	@NotNull
	private final Receiver console;

	protected Platform(String name, Receiver console) {
		this.name = name;
		this.console = console;
	}

	public static @NotNull Platform getPlatform(){
		return platform;
	}

	@NotNull
	public abstract Collection<@NotNull Receiver> getPlayers();
	@NotNull
	public Collection<@NotNull Receiver> getAllReceivers() {
		HashSet<Receiver> receivers = new HashSet<>(getPlayers());
		receivers.add(getConsole());
		return receivers;
	}
}
