package bet.astral.messenger.v2.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Component type registrar used for messengers. Used to support custom component types.
 */
public class ComponentTypeRegistry {
	private final Map<String, ComponentType> componentTypeMap = new HashMap<>();

	/**
	 * Creates a new component type registrar.
	 */
	public ComponentTypeRegistry() {
		init();
	}

	protected void init(){}

	/**
	 * Registers given the component type to the component type registrar.
	 * @param componentType component type
	 */
	public void register(@NotNull ComponentType componentType){
		this.componentTypeMap.put(componentType.getName().toLowerCase(), componentType);
		for (String alias : componentType.getAliases()){
			componentTypeMap.put(alias.toLowerCase(), componentType);
		}
	}

	/**
	 * Unregisters the given component type from the component type registrar.
	 * @param componentType component type
	 */
	public void unregister(@NotNull ComponentType componentType){
		this.componentTypeMap.remove(componentType.getName());
		for (String alias : componentType.getAliases()){
			componentTypeMap.remove(alias.toLowerCase());
		}
	}

	/**
	 * Checks if given the component type are registered in the registry.
	 * @param componentType component type
	 * @return true if registered, else false
	 */
	public boolean isRegistered(@NotNull ComponentType componentType){
		return this.componentTypeMap.containsValue(componentType);
	}

	/**
	 * Checks if given the component type name or alias are registered in the registry.
	 * @param name name or alias
	 * @return true if registered, else false
	 */
	public boolean isRegistered(@NotNull String name){
		return this.componentTypeMap.containsKey(name.toLowerCase());
	}

	/**
	 * Returns component type using alias or name of the component type.
	 * @param name name or alias
	 * @return name
	 */
	@Nullable
	public ComponentType get(@NotNull String name){
		return this.componentTypeMap.get(name);
	}

	/**
	 * Returns all registered component types
	 * @return component types
	 */
	public Set<ComponentType> getRegisteredComponentTypes(){
		return new HashSet<>(componentTypeMap.values());
	}
}
