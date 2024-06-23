package bet.astral.messenger.v2.permission;

import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

/**
 * Or permission as the name returns true if any of the given 2 permissions return true.
 */
public interface OrPermission extends Permission{
	/**
	 * Creates a new instance of {@link OrPermission<C>}
	 * @param one first permission
	 * @param two second perission
	 * @return new or permission
	 */
	@NotNull
	static OrPermission of(@NotNull Permission one, @NotNull Permission two){
		return new OrPermissionImpl(one, two);
	}
	@NotNull
	Permission getFirst();
	@NotNull
	Permission getSecond();
}
