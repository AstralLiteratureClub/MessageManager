package bet.astral.messenger.v2.component;

import net.kyori.adventure.text.Component;

class ComponentPartImpl implements ComponentPart{
	private final Component component;
	private final boolean hidePrefix;

	public ComponentPartImpl(Component component) {
		this.component = component;
		this.hidePrefix = false;
	}
	public ComponentPartImpl(Component component, boolean hidePrefix) {
		this.component = component;
        this.hidePrefix = hidePrefix;
    }

	@Override
	public Component getTextComponent() {
		return component;
	}

	@Override
	public boolean isPrefixHidden() {
		return hidePrefix;
	}
}
