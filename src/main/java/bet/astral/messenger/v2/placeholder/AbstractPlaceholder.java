package bet.astral.messenger.v2.placeholder;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractPlaceholder implements Placeholder{
	private final String key;
	protected AbstractPlaceholder(String key){
		this.key = key;
	}

	@Override
	public @NotNull String getKey() {
		return key;
	}
}
