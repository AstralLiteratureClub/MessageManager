package bet.astral.messenger.permission;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.permission.Permission;

@Deprecated(forRemoval = true)
public class OrPermission extends DoublePermission implements Permission {
	public OrPermission(bet.astral.messenger.permission.Permission one, bet.astral.messenger.permission.Permission two) {
		super(one, two);
	}

	@Override
	public @NonNull String permissionString() {
		return getOne().permissionString()+" or "+getTwo().permissionString();
	}
}
