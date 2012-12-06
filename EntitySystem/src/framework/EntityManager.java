
package framework;

import helpers.State;

import java.util.ArrayList;
import java.util.HashMap;


public class EntityManager {
    private HashMap<Class<?>, HashMap<Entity, ? extends Component>> entities = new HashMap<Class<?>, HashMap<Entity, ? extends Component>>();
    private ArrayList<Entity> deleties = new ArrayList<Entity>();
    
    public StateManager sm;
    
    public EntityManager(StateManager sm)
    {
    	this.sm = sm;
    }

    public <T extends Component> void addComponent(Entity e, T component) {
		HashMap<Entity, T> componentMap = (HashMap<Entity, T>) entities.get(component.getClass());
		if (componentMap == null) {
			componentMap = new HashMap<Entity, T>();
			entities.put(component.getClass(), componentMap);
		}
		componentMap.put(e, component);
		
		Class<?>[] ints = component.getClass().getInterfaces();
		for (Class c: ints)
		{
			HashMap<Entity, T> interfaceMap = (HashMap<Entity, T>) entities.get(c);
			if (interfaceMap == null) {
				interfaceMap = new HashMap<Entity, T>();
				entities.put(c, interfaceMap);
			}
			interfaceMap.put(e, component);
		}
    }
    
    public <T> void removeComponent(Entity e, T component) {
		HashMap<Entity, T> HashMap = (HashMap<Entity, T>) entities.get(component.getClass());
		HashMap.remove(e);
		if (entities.get(component.getClass()).isEmpty())
			entities.remove(component.getClass());
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
				result.retainAll(  getEntity(c)  );
		}

		return result;
    }

    private void remove(Entity e) {
		for (HashMap<Entity, ? extends Component> HashMap : entities.values()) {
			HashMap.remove(e);
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

	public void setState(State s)
	{
		sm.setState(s);
	}
}
