package bet.astral.messenger.placeholder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Placeholder {
	protected static final MiniMessage miniMessage = MiniMessage.miniMessage();
	protected static final LegacyComponentSerializer legacyAmpersand = LegacyComponentSerializer.legacyAmpersand();
	protected static final LegacyComponentSerializer legacySection = LegacyComponentSerializer.legacySection();
	@Nullable
	private final Component componentValue;
	@NotNull
	private final String stringValue;
	@NotNull
	private final String key;
	private final boolean isComponentValue;
	private final boolean isLegacy;

	public Placeholder(@NotNull String key, @NotNull Component componentValue) {
		this.componentValue = componentValue;
		this.stringValue = MiniMessage.miniMessage().serialize(componentValue);
		this.key = key;
		this.isComponentValue = true;
		this.isLegacy = false;
	}

	public Placeholder(@NotNull String key, @NotNull String stringValue, boolean isLegacy) {
		this.componentValue = isLegacy ? legacySection.deserialize(stringValue) : miniMessage.deserialize(stringValue);
		this.stringValue = stringValue;
		this.key = key;
		this.isComponentValue = false;
		this.isLegacy = isLegacy;
	}
	public Placeholder(@NotNull String key, @NotNull String stringValue) {
		this(key, stringValue, false);
	}

	public Placeholder(@NotNull String key, @NotNull Object objectValue) {
		this(key, objectValue.toString(), false);
	}

	public Placeholder(@NotNull String key, @NotNull Object objectValue, boolean isLegacy) {
			this(key, objectValue.toString(), isLegacy);
	}


	public static Placeholder emptyPlaceholder(@NotNull String key){
		return new Placeholder(key, key, false);
	}


	@Nullable
	public Component componentValue() {
		if (componentValue == null && isLegacy){
			MiniMessage miniMessage = MiniMessage.miniMessage();
			LegacyComponentSerializer serializerAmpersand = LegacyComponentSerializer.legacyAmpersand();
			LegacyComponentSerializer serializerSection = LegacyComponentSerializer.legacySection();
			Component component = serializerSection.deserialize(stringValue);
			String serialized = miniMessage.serialize(component);
			component = serializerAmpersand.deserialize(serialized);
			return component;
		}
		return componentValue;
	}

	public String stringValue() {
		return stringValue;
	}

	public String key() {
		return key;
	}

	public boolean isComponentValue() {
		return isComponentValue;
	}
}
