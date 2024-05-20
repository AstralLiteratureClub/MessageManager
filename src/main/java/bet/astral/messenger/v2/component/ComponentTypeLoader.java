package bet.astral.messenger.v2.component;

import bet.astral.messenger.v2.locale.source.components.BasicComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Loads given cached basic component as a component part.
 */
public interface ComponentTypeLoader {
	/**
	 * Converts cached component to component part
	 * @param basicComponent cached component
	 * @return component part
	 */
	ComponentPart load(@NotNull BasicComponent basicComponent);
}
