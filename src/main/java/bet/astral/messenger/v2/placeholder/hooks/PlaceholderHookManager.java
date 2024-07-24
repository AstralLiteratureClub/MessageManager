package bet.astral.messenger.v2.placeholder.hooks;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface PlaceholderHookManager {
	static PlaceholderHookManager getGlobal(){
		return PlaceholderHookManagerImpl.get();
	}
	static PlaceholderHookManager getGlobalClone(){
		return PlaceholderHookManagerImpl.cloneGlobal();
	}
	@NotNull
	Set<PlaceholderHook> getHooks();

	void register(@NotNull PlaceholderHook hook);
	void unregister(@NotNull PlaceholderHook hook);
}