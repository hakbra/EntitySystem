
package framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import framework.components.Hero;



public class EntityManager{
	
    public HashMap<Class<?>, HashMap<Entity, ? extends Component>> entities = new HashMap<Class<?>, HashMap<Entity, ? extends Component>>();
    public HashMap<String, Entity> stringIDs = new HashMap<String, Entity>();
    private ArrayList<Entity> deleties = new ArrayList<Entity>();
    public ArrayList<Entity> renders = new ArrayList<Entity>();
    
    public World world;
    
    public EntityManager(World w)
    {
    	this.world = w;

    }

    public void addStringID(Entity e)
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
    public Entity getByStringID(String s)
    {
    	return stringIDs.get(s);
    }

    public <T extends Component> void addComponent(Entity e, T component) {
		HashMap<Entity, T> componentMap = (HashMap<Entity, T>) entities.get(component.getClass());
		if (componentMap == null) {
			componentMap = new HashMap<Entity, T>();
			entities.put(component.getClass(), componentMap);
		}
		componentMap.put(e, component);
		
		for (Component c : e.components)
			c.entityUpdated(this, e);
		
		e.components.add(component);
		
		if (e.layer != Layer.NOT && !renders.contains(e))
			addRender(e);
    }
    
    public <T> void removeComponent(Entity e, T component) {
		HashMap<Entity, T> HashMap = (HashMap<Entity, T>) entities.get(component.getClass());
		HashMap.remove(e);
		if (entities.get(component.getClass()).isEmpty())
			entities.remove(component.getClass());

		
		e.components.remove(component);
		
		for (Component c : e.components)
			c.entityUpdated(this, e);
    }

    public <T> T getComponent(Entity e, Class<T> type) {
		HashMap<Entity, T> HashMap = (HashMap<Entity, T>) entities.get(type);
		if (HashMap != null)
			return HashMap.get(e);
		return null;
    }

    public <T> boolean hasComponent(Entity e, Class<T> type) {
		HashMap<Entity, ? extends Component> HashMap = entities.get(type);
		if (HashMap == null)
			return false;
		else
			return HashMap.containsKey(e);
    }

    public <T> boolean hasComponents(Entity e, Class<? extends Component>... types){
    	for (Class<? extends Component> c : types){
			if (!hasComponent(e,c))
				return false;
		}
		return true;
    }
    
    public ArrayList<Entity> getEntity(Class<?> type) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		HashMap<Entity, ? extends Component> HashMap = entities.get(type);
		if (HashMap != null)
			result.addAll(HashMap.keySet());
		
		return result;
    }

    public ArrayList<Entity> getEntityAll(Class<?>... types) {
    	ArrayList<Entity> result = new ArrayList<Entity>();
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

    private void remove(Entity e) {
		for (HashMap<Entity, ? extends Component> HashMap : entities.values()) {
			HashMap.remove(e);
			stringIDs.remove(e.name);
			renders.remove(e);
		}
    }
    
    public void removeEntity(Entity e)
    {
    	deleties.add(e);
    }
    
    public void removeEntities()
    {
    	for (Entity e : deleties)
    		remove(e);
    	
    	deleties.clear();
    }

	public void addEntity(Entity e) {
		for (Component c : e.components)
		{
			HashMap<Entity, Component> componentMap = (HashMap<Entity, Component>) entities.get(c.getClass());
			if (componentMap == null) {
				componentMap = new HashMap<Entity, Component>();
				entities.put(c.getClass(), componentMap);
			}
			componentMap.put(e, c);
		}
		for (Component c : e.components)
			c.entityUpdated(this, e);
		
		if (e.layer != Layer.NOT)
			addRender(e);
	}

	private void addRender(Entity e)
	{
		if (e.layer == Layer.NOT)
		{
			System.out.println("You must specify layer - Entity: " + e.name);
			System.exit(0);
		}
		renders.add (e);
		Collections.sort(renders);
	}
}
