package bet.astral.messenger.v2.configuration;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@Getter
public class ComponentPartConf {
	private final Component component;
	public ComponentPartConf(@NotNull Component component) {
		this.component = component;
	}
	protected ComponentPartConf(){
		this.component = null;
	}
}
