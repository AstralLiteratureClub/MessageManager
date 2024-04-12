package bet.astral.messenger.placeholder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.minecraft.extras.caption.RichVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Placeholder implements CaptionVariable, ComponentLike, RichVariable {
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

	public Placeholder(@NotNull String key, @NotNull RichVariable richVariable) {
		this(key, richVariable.component());
	}
	public Placeholder(@NotNull String key, @NotNull CaptionVariable variable){
		this(key, variable.value());
	}
	public Placeholder(@NotNull String key, @NotNull Object objectValue) {
		this(key, objectValue.toString(), false);
	}

	public Placeholder(@NotNull String key, @NotNull Object objectValue, boolean isLegacy) {
		this(key, objectValue.toString(), isLegacy);
	}

	protected Placeholder(@NotNull String key, boolean isComponentValue) {
		this.componentValue = null;
		this.stringValue = "";
		this.key = key;
		this.isComponentValue = isComponentValue;
		this.isLegacy = false;
	}

	public static Placeholder of(CaptionVariable variable){
		return new Placeholder(variable.key(), variable.value());
	}

	public static PlaceholderList of(List<CaptionVariable> variables){
		return new PlaceholderList(variables.stream().map(Placeholder::of).toList());
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

	@NotNull
	public String key() {
		return key;
	}

	@Override
	public @NonNull Component component() {
		return asComponent();
	}

	@Override
	public @NonNull String value() {
		return stringValue;
	}

	public boolean isComponentValue() {
		return isComponentValue;
	}

	@Override
	public @NotNull Component asComponent() {
		if (componentValue == null){
			if (isLegacy){
				return legacyAmpersand.deserialize(stringValue);
			} else {
				return miniMessage.deserialize(stringValue);
			}
		}
		return componentValue;
	}
}
