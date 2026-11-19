package bet.astral.messenger.v3.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ComponentSplit {

    private ComponentSplit() {
    }

    /**
     * Splits a component and its children using one or more regex separators.
     * Example: {@code ComponentSplit.split(component, "\n", "%nl%")}
     *
     * @param self       The component to split.
     * @param separators One or more regex patterns to split on.
     * @return A list of split components with styles preserved.
     */
    @Contract(pure = true)
    public static @NotNull List<Component> split(
            final @NotNull Component self,
            final @NotNull @RegExp String... separators
    ) {
        if (separators.length == 0) {
            return List.of(self);
        }
        String combined = Arrays.stream(separators)
                .map(s -> "(?:" + s + ")")
                .collect(Collectors.joining("|"));
        return split(self, Pattern.compile(combined));
    }

    /**
     * Splits a component and its children using one or more literal (non-regex) separators.
     * Safe for separators containing regex metacharacters like {@code $}, {@code .}, or {@code []}.
     *
     * @param self       The component to split.
     * @param separators One or more literal strings to split on.
     * @return A list of split components with styles preserved.
     */
    @Contract(pure = true)
    public static @NotNull List<Component> splitLiteral(
            final @NotNull Component self,
            final @NotNull String... separators
    ) {
        if (separators.length == 0) {
            return List.of(self);
        }
        String combined = Arrays.stream(separators)
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
        return split(self, Pattern.compile(combined));
    }

    /**
     * Splits a component and its children using a pre-compiled {@link Pattern}.
     *
     * @param self    The component to split.
     * @param pattern The compiled regex pattern to split on.
     * @return A list of split components with styles preserved.
     */
    @Contract(pure = true)
    public static @NotNull List<Component> split(
            final @NotNull Component self,
            final @NotNull Pattern pattern
    ) {
        List<Component> lines = splitComponentContent(self, pattern);

        if (self.children().isEmpty()) {
            return lines;
        }

        // Extract the last segment, which becomes the parent for the first child segment
        Component parent = lines.remove(lines.size() - 1);

        for (Component child : self.children()) {
            List<Component> childSegments = split(child, pattern);

            // First segment stays on the current line
            parent = parent.append(childSegments.get(0));

            // Subsequent segments start new lines inheriting the parent's style
            for (int i = 1; i < childSegments.size(); i++) {
                lines.add(parent);
                parent = Component.empty().style(parent.style()).append(childSegments.get(i));
            }
        }

        lines.add(parent);
        return lines;
    }

    private static @NotNull List<Component> splitComponentContent(
            final @NotNull Component component,
            final @NotNull Pattern pattern
    ) {
        if (!(component instanceof TextComponent textComponent)) {
            // Return a mutable list and strip children so they aren't duplicated when re-appended
            List<Component> result = new ArrayList<>(1);
            result.add(component.children(List.of()));
            return result;
        }

        // Limit -1 preserves trailing empty strings (e.g. "line1\n" -> ["line1", ""])
        String[] segments = pattern.split(textComponent.content(), -1);
        List<Component> result = new ArrayList<>(segments.length);

        for (String segment : segments) {
            result.add(Component.text(segment).style(textComponent.style()));
        }

        return result;
    }
}