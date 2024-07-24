package bet.astral.messenger.v2;

import org.jetbrains.annotations.NotNull;

/**
 * Clones given an object with the name being given new name
 */
public interface CloneableWithName extends Cloneable{
	/**
	 * Clones this and with the name being the param name
	 * @param name new name
	 * @return new clone, with given name as the name
	 */
	@NotNull
	CloneableWithName clone(@NotNull String name);
}
