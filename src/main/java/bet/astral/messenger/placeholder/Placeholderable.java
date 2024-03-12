package bet.astral.messenger.placeholder;

import java.util.Collection;

public interface Placeholderable {
	Collection<Placeholder> asPlaceholder(String prefix);
}
