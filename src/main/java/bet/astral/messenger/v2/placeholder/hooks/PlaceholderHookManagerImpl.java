package bet.astral.messenger.v2.placeholder.hooks;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class PlaceholderHookManagerImpl implements PlaceholderHookManager{
	static PlaceholderHookManagerImpl manager = null;

	static PlaceholderHookManagerImpl get(){
		if (manager == null){
			manager = new PlaceholderHookManagerImpl();
		}
		return manager;
	}
	static PlaceholderHookManager cloneGlobal(){
		return new PlaceholderHookManagerImpl(get());
	}

	private final Map<String, PlaceholderHook> hookMap;

	public PlaceholderHookManagerImpl(PlaceholderHookManagerImpl placeholderHookManager) {
		this.hookMap = placeholderHookManager.hookMap;
	}

	public PlaceholderHookManagerImpl(){
		this.hookMap = new HashMap<>();
	}

	@Override
	public @NotNull Set<PlaceholderHook> getHooks() {
		return new HashSet<>(hookMap.values());
	}

	@Override
	public void register(@NotNull PlaceholderHook hook) {
		hookMap.put(hook.getName().toLowerCase(), hook);
	}

	@Override
	public void unregister(@NotNull PlaceholderHook hook) {
		hookMap.remove(hook.getName().toLowerCase());
	}
}
