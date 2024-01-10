package bet.astral.messagemanager.placeholder;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class LegacyPlaceholder extends Placeholder {
	private static Component deserializeLegacy(@NotNull final String legacyValue){
		Component modernValue = legacyAmpersand.deserialize(legacyValue);
		String serialized = legacySection.serialize(modernValue);
		modernValue = legacySection.deserialize(serialized);
		return modernValue;
	}
	public LegacyPlaceholder(@NotNull String key, @NotNull String legacyValue) {
		super(key, deserializeLegacy(legacyValue));
	}

	@Override
	public boolean isComponentValue() {
		return true;
	}
}
