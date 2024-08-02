package bet.astral.messenger.v2.locale.source.components;

import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

@Getter
@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "2.1.0")
public class BasicComponent {
	private final String component;

	public BasicComponent(String component) {
		this.component = component;
	}
}
