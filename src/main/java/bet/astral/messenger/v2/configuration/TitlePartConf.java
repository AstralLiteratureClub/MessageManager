package bet.astral.messenger.v2.configuration;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.time.Duration;

@ConfigSerializable
public class TitlePartConf extends ComponentPartConf{
	private final Duration fadeIn;
	private final Duration stay;
	private final Duration fadeOut;
	public TitlePartConf(@NotNull Component component, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
		super(component);
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}
	private TitlePartConf(){
		this.fadeIn = null;
		this.stay = null;
		this.fadeOut = null;
	}
}
