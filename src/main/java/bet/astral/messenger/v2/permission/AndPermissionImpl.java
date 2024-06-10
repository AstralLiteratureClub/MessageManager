package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

final class AndPermissionImpl<C extends Permissionable> implements AndPermission<C> {
	@NotNull
	private final Permission<C> one;
	@NotNull
	private final Permission<C> two;

	AndPermissionImpl(@NotNull Permission<C> one, @NotNull Permission<C> two) {
		this.one = one;
		this.two = two;
	}

	@Override
	public @NotNull Permission<C> getFirst() {
		return one;
	}

	@Override
	public @NotNull Permission<C> getSecond() {
		return two;
	}

	@Override
	public boolean test(@NotNull C receiver) {
		return one.test(receiver) && two.test(receiver);
	}
}
