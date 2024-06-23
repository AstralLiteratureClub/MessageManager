package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

/**
 * Requires both permissions to be true to return true
 */
public interface AndPermission extends Permission{
	/**
	 * Creates a new instance of {@link AndPermission}
	 * @param one first permission
	 * @param two second permission
	 * @return combined permission
	 */
	@NotNull
	static AndPermission of(@NotNull Permission one, @NotNull Permission two){
		return new AndPermissionImpl(one, two);
	}

	/**
	 * The first permission
	 * @return permission
	 */
	@NotNull
	Permission getFirst();

	/**
	 * The second permission
	 * @return permission
	 */
	@NotNull
	Permission getSecond();
}
