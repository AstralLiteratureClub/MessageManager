package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.platform.annotations.Immutable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Represents a base component message which can send multiple types of messages
 */
public interface ComponentBase {
	/**
	 * Returns the translation key for this base component base
	 * @return key
	 */
	@NotNull
	TranslationKey getTranslationKey();

	/**
	 * Returns the parts this component part supports.
	 * @return parts, else null
	 */
	@Nullable
	@Immutable
	Map<@NotNull ComponentType, @NotNull ComponentPart> getParts();

	/**
	 * Returns built in placeholders for this base component
	 * @return placeholders
	 */
	@Nullable
	@Immutable
	Map<String, Placeholder> getPlaceholders();

	/**
	 * Returns true if this base component is disabled.
	 * @return true if disabled, else false
	 */
	boolean isDisabled();

	/**
	 * Disables/Enables this base component
	 * @param disable true if disable, false if enable
	 */
	void setDisabled(boolean disable);
}
