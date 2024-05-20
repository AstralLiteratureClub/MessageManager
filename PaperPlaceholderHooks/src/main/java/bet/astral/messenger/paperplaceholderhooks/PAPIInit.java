package bet.astral.messenger.paperplaceholderhooks;

import bet.astral.messenger.v2.placeholder.hooks.PlaceholderHookManager;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import java.util.Collection;
import java.util.LinkedList;

public final class PAPIInit {
	public static void hook(PlaceholderHookManager hookManager){
		Collection<PlaceholderExpansion> expansions = new LinkedList<>(PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().getExpansions());
		expansions.forEach(placeholderExpansion -> hookManager.register(new PAPIHook(placeholderExpansion)));
	}
}
