package bet.astral.messenger.message.part;

import bet.astral.messenger.message.MessageType;
import bet.astral.messenger.message.serializer.IMessageTypeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class RandomMessagePart<C> extends DefaultMessagePart<C> implements IMessagePart<C> {
	public static final Random random = new Random((long) (System.currentTimeMillis()*(((12+Math.random())-3.5)/Math.random())));
	private final List<C> components;

	public RandomMessagePart(@NotNull MessageType type, @NotNull List<C> values, @NotNull IMessageTypeSerializer<C> serializer) {
		super(type, null, serializer);
		this.components = values;
	}

	public RandomMessagePart(@NotNull MessageType type){
		super(type);
		this.components = null;
	}

	@Override
	public C asComponent() {
		if (components == null || components.isEmpty()){
			return null;
		}
		if (components.size() == 1){
			return components.get(0);
		}
		return components.get(random.nextInt(1, components.size())-1);
	}

	public List<C> possibleOutComes() {
		return components;
	}

	@Override
	public String asString() {
		C component = asComponent();
		if (component == null){
			return null;
		}
		if (getSerializer() != null){
			return getSerializer().serialize(component);
		}
		return component.toString();
	}
}
