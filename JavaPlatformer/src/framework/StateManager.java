package framework;

import helpers.State;

import java.util.ArrayList;
import java.util.HashMap;

public class StateManager {
	
	State state = State.MENU;
	
    private HashMap<State, ArrayList<CoreSystem>> systemlists = new HashMap<State, ArrayList<CoreSystem>>();
    private HashMap<State, EntityManager> managers = new HashMap<State, EntityManager>();
    
    public void setState(State s)
    {
    	this.state = s;
    }
    
    public void addSystem(State state, CoreSystem system)
    {
    	ArrayList<CoreSystem> systemlist = systemlists.get(state);
    	if (systemlist == null)
    	{
    		systemlist = new ArrayList<CoreSystem>();
    		systemlists.put(state, systemlist);
    	}
    	systemlist.add(system);
    }

    public void addComponent(State state, Entity entity, Component component)
    {
    	EntityManager em = managers.get(state);
    	if (em == null)
    	{
    		em = new EntityManager();
    		managers.put(state, em);
    	}
    	
    	em.addComponent(entity, component);
    }
    
    public void runSystems()
    {
    	ArrayList<CoreSystem> systemlist = systemlists.get(state);
    	if (systemlist == null)
    		systemlist = new ArrayList<CoreSystem>();

    	EntityManager em = managers.get(state);
    	if (em == null)
    		em = new EntityManager();
    	
    	for (CoreSystem sys : systemlist)
    		sys.run(em);
    }

}
