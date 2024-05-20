package bet.astral.messenger.v2.cloud.placeholders;

import org.incendo.cloud.caption.CaptionVariable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VariablePlaceholderCollections {
	public static VariablePlaceholder[] convert(@NotNull CaptionVariable... captionVariables){
		return Stream.of(captionVariables).map(VariablePlaceholder::of).toArray(VariablePlaceholder[]::new);
	}
	public static Collection<VariablePlaceholder> toList(@NotNull CaptionVariable... captionVariables){
		return Stream.of(captionVariables).map(bet.astral.messenger.v2.cloud.placeholders.VariablePlaceholder::of).collect(Collectors.toList());
	}
	public static @NotNull Collection<VariablePlaceholder> toList(@NotNull List<CaptionVariable> captionVariables){
		return captionVariables.stream().map(bet.astral.messenger.v2.cloud.placeholders.VariablePlaceholder::of).collect(Collectors.toList());
	}
}
