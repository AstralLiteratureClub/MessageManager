package bet.astral.messenger.v2.placeholder;

import org.jetbrains.annotations.NotNull;

/**
 * Reference to a placeholder. Has a placeholder in the behind of the scenes return values
 */
public interface PlaceholderReference extends Placeholder{
	/**
	 * Returns the referenced placeholder
	 * @return placeholder
	 */
	@NotNull
	Placeholder getReference();
}
