package bet.astral.messenger.v2.component;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a parsed component part from messenger
 * @param componentPart component part
 * @param parsedComponent the component parsed in messenger
 */
public record ParsedComponentPart(@NotNull ComponentPart componentPart, @NotNull Component parsedComponent) {
	public ParsedComponentPart(ComponentPart componentPart, Component parsedComponent) {
		this.componentPart = componentPart;
		this.parsedComponent = parsedComponent;
	}
}
