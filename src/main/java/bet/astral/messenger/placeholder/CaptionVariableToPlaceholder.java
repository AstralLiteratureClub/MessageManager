package bet.astral.messenger.placeholder;

import org.incendo.cloud.caption.CaptionVariable;

import java.util.List;
import java.util.stream.Collectors;

public interface CaptionVariableToPlaceholder{
	static Placeholder apply(CaptionVariable captionVariable) {
		if (captionVariable instanceof Placeholder placeholder) {
			return placeholder;
		}
		return Placeholder.of(captionVariable);
	}
	static List<Placeholder> apply(List<CaptionVariable> variables){
		return variables.stream().map(CaptionVariableToPlaceholder::apply).collect(Collectors.toList());
	}
}
