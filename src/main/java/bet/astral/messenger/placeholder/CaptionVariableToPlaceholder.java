package bet.astral.messenger.placeholder;

import net.kyori.adventure.text.ComponentLike;
import org.incendo.cloud.caption.CaptionVariable;

import java.util.List;
import java.util.stream.Collectors;

public interface CaptionVariableToPlaceholder{
	static Placeholder apply(CaptionVariable captionVariable) {
		if (captionVariable instanceof Placeholder placeholder) {
			return placeholder;
		} else if (captionVariable instanceof ComponentLike componentLike){
			return new Placeholder(captionVariable.key(), componentLike.asComponent());
		}
		return Placeholder.of(captionVariable);
	}
	static List<Placeholder> apply(List<CaptionVariable> variables){
		return variables.stream().map(CaptionVariableToPlaceholder::apply).collect(Collectors.toList());
	}
}
