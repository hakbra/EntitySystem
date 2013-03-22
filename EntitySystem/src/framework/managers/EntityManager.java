
package framework.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import framework.CoreComponent;
import framework.CoreEntity;
import framework.DynEnum;
import framework.World;

public class EntityManager{
	
    public HashMap<Class<?>, HashMap<CoreEntity, ? extends CoreComponent>> entities = new HashMap<Class<?>, HashMap<CoreEntity, ? extends CoreComponent>>();
    public HashMap<String, CoreEntity> stringIDs = new HashMap<String, CoreEntity>();
    private ArrayList<CoreEntity> deleties = new ArrayList<CoreEntity>();
    public ArrayList<CoreComponent> renders = new ArrayList<CoreComponent>();
    
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

    public <T extends CoreComponent> void addComponent(CoreEntity e, T component) {
		HashMap<CoreEntity, T> componentMap = (HashMap<CoreEntity, T>) entities.get(component.getClass());
		if (componentMap == null) {
			componentMap = new HashMap<CoreEntity, T>();
			entities.put(component.getClass(), componentMap);
		}
		componentMap.put(e, component);
		
		e.components.add(component);
		component.parent = e;
		component.world = this.world;

		if (component.layer != DynEnum.at("layer").get("null"))
			addRender(component);
    }
    
    public <T> void removeComponent(CoreEntity e, T component) {
		HashMap<CoreEntity, T> HashMap = (HashMap<CoreEntity, T>) entities.get(component.getClass());
		HashMap.remove(e);
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
		{
			HashMap<CoreEntity, CoreComponent> componentMap = (HashMap<CoreEntity, CoreComponent>) entities.get(c.getClass());
			if (componentMap == null) {
				componentMap = new HashMap<CoreEntity, CoreComponent>();
				entities.put(c.getClass(), componentMap);
			}
			componentMap.put(e, c);
			c.parent = e;
			c.world = this.world;
			if (c.layer != DynEnum.at("layer").get("null"))
				addRender(c);
		}
	}

	private void addRender(CoreComponent e)
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
