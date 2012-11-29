
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

    public <T extends Component> Entity addComponent(Entity e, T component) {
		HashMap<Entity, T> HashMap = (HashMap<Entity, T>) entities.get(component .getClass());
		if (HashMap == null) {
			HashMap = new HashMap<Entity, T>();
			entities.put(component.getClass(), HashMap);
		}
		HashMap.put(e, component);
		return e;
    }
    
    public <T extends Component> Entity removeComponent(Entity e, T component) {
		HashMap<Entity, T> HashMap = (HashMap<Entity, T>) entities.get(component.getClass());
		HashMap.remove(e);
		if (entities.get(component.getClass()).isEmpty()) {
			entities.remove(component.getClass());
		}
		return e; 
    }

    public <T extends Component> T getComponent(Entity e, Class<T> type) {
		HashMap<Entity, T> HashMap = (HashMap<Entity, T>) entities.get(type);
		if (HashMap != null)
			return HashMap.get(e);
		return null;
    }

    public <T extends Component> boolean hasComponent(Entity e, Class<T> type) {
		HashMap<Entity, ? extends Component> HashMap = entities.get(type);
		if (HashMap == null)
			return false;
		else
			return HashMap.containsKey(e);
    }
    
    public <T extends Component> boolean hasComponents(Entity e, ArrayList<Class<? extends Component>> types){
		for (Class<? extends Component> c : types){
			if (!hasComponent(e,c))
				return false;
		}
		return true;
    }

    public <T extends Component> boolean hasComponents(Entity e, Class<? extends Component>... types){
    	for (Class<? extends Component> c : types){
			if (!hasComponent(e,c))
				return false;
		}
		return true;
    }
    public <T extends Component> ArrayList<Entity> get(Class<T> type) {
		ArrayList<Entity> result = new ArrayList<Entity>();
		HashMap<Entity, ? extends Component> HashMap = entities.get(type);
		if (HashMap != null)
			result.addAll(HashMap.keySet());
		
		return result;
    }

    public ArrayList<Entity> getAll(Class<? extends Component>... types) {
    	ArrayList<Entity> result = new ArrayList<Entity>();
    	boolean first = true;
    	
    	for (Class<? extends Component> c : types){
			if (first)
			{
				result.addAll(  get(c)  );
				first = false;
			}
			else
				result.retainAll(  get(c)  );
		}

		return result;
    }

    public ArrayList<Entity> getAll(ArrayList<Class<? extends Component>> types) {
    	ArrayList<Entity> result = new ArrayList<Entity>();
    	
    	if (types.size() == 0)
    		return result;
    	
    	result.addAll(  get(types.get(0))  );
    	
		for (int i = 1; i < types.size(); i++)
			result.retainAll(get(types.get(i)));

		return result;
    }

    private void remove(Entity e) {
		for (HashMap<Entity, ? extends Component> HashMap : entities.values()) {
			HashMap.remove(e);
		}
    }

    public ArrayList<Class<? extends Component>> getComponents(Entity e) {
    	ArrayList<Class<? extends Component>> comps = new ArrayList<Class<? extends Component>>();
		for (Class c : entities.keySet()) {
			if (entities.get(c).get(e) != null)
				comps.add(c);
		}
		return comps;
    }
    
    public void removeLater(Entity e)
    {
    	deleties.add(e);
    }
    
    public void doRemoval()
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
