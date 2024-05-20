package bet.astral.messenger.v2.component;

import net.kyori.adventure.text.Component;

class ComponentPartImpl implements ComponentPart{
	private final Component component;

	public ComponentPartImpl(Component component) {
		this.component = component;
	}

	@Override
	public Component getTextComponent() {
		return component;
	}
}
