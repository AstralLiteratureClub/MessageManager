package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

final class OrPermissionImpl<C extends Permissionable> implements OrPermission<C> {
	@NotNull
	private final Permission<C> one;
	@NotNull
	private final Permission<C> two;

	public OrPermissionImpl(@NotNull Permission<C> one, @NotNull Permission<C> two) {
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
	public boolean test(@NotNull C permissionable) {
		return one.test(permissionable) || two.test(permissionable);
	}
}
