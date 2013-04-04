
package framework.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import zombies.components.RenderComponent;
import framework.CoreComponent;
import framework.CoreEntity;
import framework.DynEnum;
import framework.World;

public class EntityManager{
	
    public HashMap<Class<?>, HashMap<CoreEntity, ? extends CoreComponent>> entities = new HashMap<Class<?>, HashMap<CoreEntity, ? extends CoreComponent>>();
    public HashMap<String, CoreEntity> stringIDs = new HashMap<>();
    private ArrayList<CoreEntity> deleties = new ArrayList<>();
    public ArrayList<RenderComponent> renders = new ArrayList<>();
    
    public World world;
    public int state;
    
    public EntityManager(World w, int s)
    {
    	this.world = w;
    	this.state = s;
    }

    public void addStringID(CoreEntity e)
    {
    	if (e.name == null)
    	{
    		System.out.println("EntityManager: Cannot add empty name");
    		return;
    	}
    	
    	if (stringIDs.get(e.name) != null)
    	{
    		System.out.println("EntityManager: " + e.name + " already added");
    		return;
    	}
    	
    	stringIDs.put(e.name, e);
    }
    public CoreEntity getByStringID(String s)
    {
    	return stringIDs.get(s);
    }

    private <T extends CoreComponent> void registerSuperComponent(CoreEntity e, T component)
    {
		HashMap<CoreEntity, T> componentMap = (HashMap<CoreEntity, T>) entities.get(component.getClass().getSuperclass());
		if (componentMap == null) {
			componentMap = new HashMap<CoreEntity, T>();
			entities.put(component.getClass().getSuperclass(), componentMap);
		}
		componentMap.put(e, component);
    }
    private <T extends CoreComponent> void registerComponent(CoreEntity e, T component)
    {
		HashMap<CoreEntity, T> componentMap = (HashMap<CoreEntity, T>) entities.get(component.getClass());
		if (componentMap == null) {
			componentMap = new HashMap<CoreEntity, T>();
			entities.put(component.getClass(), componentMap);
		}
		componentMap.put(e, component);
    }

    public <T extends CoreComponent> void addComponent(CoreEntity e, T component) {
    	component.parent = e;
    	component.world = this.world;
		registerComponent(e, component);
		Class sup = component.getClass().getSuperclass();
		if (sup != null && CoreComponent.class != sup)
			registerSuperComponent(e, component);
		if (!e.components.contains(component))
			e.components.add(component);

		if (sup == RenderComponent.class)
			addRender((RenderComponent) component);
    }
    
    public <T> void removeComponent(CoreEntity e, T component) {
		HashMap<CoreEntity, T> hashMap = (HashMap<CoreEntity, T>) entities.get(component.getClass());
		hashMap.remove(e);
		
		Class sup = component.getClass().getSuperclass();
		if (sup != null && CoreComponent.class != sup) {
			HashMap<CoreEntity, T> superHashMap = (HashMap<CoreEntity, T>) entities.get(component.getClass().getSuperclass());
			superHashMap.remove(e);
		}
		
		if (entities.get(component.getClass()).isEmpty())
			entities.remove(component.getClass());
		
		e.components.remove(component);
    }

    public <T> T getComponent(CoreEntity e, Class<T> type) {
		HashMap<CoreEntity, T> HashMap = (HashMap<CoreEntity, T>) entities.get(type);
		if (HashMap != null)
			return HashMap.get(e);
		System.out.println(e.name + " har ikke komponent " + type.getSimpleName());
		return null;
    }

    public <T> boolean hasComponent(CoreEntity e, Class<T> type) {
		HashMap<CoreEntity, ? extends CoreComponent> HashMap = entities.get(type);
		if (HashMap == null)
			return false;
		else
			return HashMap.containsKey(e);
    }

    public <T> boolean hasComponents(CoreEntity e, Class<? extends CoreComponent>... types){
    	for (Class<? extends CoreComponent> c : types){
			if (!hasComponent(e,c))
				return false;
		}
		return true;
    }
    
    public ArrayList<CoreEntity> getEntity(Class<?> type) {
		ArrayList<CoreEntity> result = new ArrayList<CoreEntity>();
		HashMap<CoreEntity, ? extends CoreComponent> HashMap = entities.get(type);
		if (HashMap != null)
			result.addAll(HashMap.keySet());
		
		return result;
    }

    public ArrayList<CoreEntity> getEntityAll(Class<?>... types) {
    	ArrayList<CoreEntity> result = new ArrayList<CoreEntity>();
    	boolean first = true;
    	
    	for (Class<?> c : types){
			if (first)
			{
				result.addAll(  getEntity(c)  );
				first = false;
			}
			else
			{
				result.retainAll(  getEntity(c)  );
			}
		}

		return result;
    }

    private void remove(CoreEntity e) {
    	for (CoreComponent c : e.components)
    		renders.remove(c);
		for (HashMap<CoreEntity, ? extends CoreComponent> HashMap : entities.values()) {
			HashMap.remove(e);
			stringIDs.remove(e.name);
		}
    }
    
    public void removeEntity(CoreEntity e)
    {
    	deleties.add(e);
    }
    
    public void removeEntities()
    {
    	for (CoreEntity e : deleties)
    		remove(e);
    	
    	deleties.clear();
    }

	public void addEntity(CoreEntity e) {
		for (CoreComponent c : e.components)
			addComponent(e, c);
	}

	private void addRender(RenderComponent e)
	{
		if (e.layer == DynEnum.at("layer").get("null"))
		{
			System.out.println("You must specify layer - CoreEntity: " + e.toString());
			System.exit(0);
		}
		renders.add (e);
		Collections.sort(renders);
	}
}
