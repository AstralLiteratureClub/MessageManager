package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.receiver.Receiver;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

class ComponentTypeImpl implements ComponentType {
	@NotNull
	private final String name;
	@NotNull
	private final ComponentType.ComponentForwarder forwarder;
	@NotNull
	private final Collection<String> aliases;

	public ComponentTypeImpl(@NotNull String name, @NotNull ComponentType.ComponentForwarder forwarder, @NotNull Collection<String> aliases) {
		this.name = name;
		this.forwarder = forwarder;
		this.aliases = ImmutableList.copyOf(aliases);
	}

	@Override
	public @NotNull String getName() {
		return name;
	}

	@Override
	public @NotNull Collection<String> getAliases() {
		return aliases;
	}

	@Override
	public void forward(@NotNull Receiver receiver, @NotNull ComponentPart componentPart) {
		forwarder.send(receiver, componentPart);
	}

}
