package bet.astral.messenger.v2.permission;

import org.jetbrains.annotations.NotNull;

final class OrPermissionImpl implements OrPermission {
	@NotNull
	private final Permission one;
	@NotNull
	private final Permission two;

	public OrPermissionImpl(@NotNull Permission one, @NotNull Permission two) {
		this.one = one;
		this.two = two;
	}

	@Override
	public @NotNull Permission getFirst() {
		return one;
	}

	@Override
	public @NotNull Permission getSecond() {
		return two;
	}

	@Override
	public boolean test(@NotNull Permissionable permissionable) {
		return one.test(permissionable) || two.test(permissionable);
	}
}
