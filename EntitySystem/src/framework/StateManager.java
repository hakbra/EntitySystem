package framework;

import helpers.State;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class StateManager {

	private boolean DEBUG = false;
	State state = State.MENU;

	private HashMap<State, ArrayList<CoreSystem>> systemlists;
	private HashMap<State, EntityManager> managers;

	public StateManager(State s)
	{
		this.state = s;
		this.systemlists = new HashMap<State, ArrayList<CoreSystem>>();
		this.managers = new HashMap<State, EntityManager>();
	}

	public boolean run()
	{
		return state != State.EXIT;
	}

	public void setState(State s)
	{
		this.state = s;
		if (DEBUG)
		{
			System.out.println("State is now " + s);
			System.out.println("Systems");
			System.out.println(getSysList(s));
		}
	}

	private EntityManager getManager(State s)
	{
		EntityManager em = managers.get(s);
		if (em == null)
		{
			em = new EntityManager(this);
			managers.put(s, em);
		}
		return em;
	}

	private ArrayList<CoreSystem> getSysList(State s)
	{
		ArrayList<CoreSystem> systemlist = systemlists.get(s);
		if (systemlist == null)
		{
			systemlist = new ArrayList<CoreSystem>();
			systemlists.put(s, systemlist);
		}
		return systemlist;
	}

	public void addSystem(State s, Class system)
	{
		EntityManager em = getManager(s);
		ArrayList<CoreSystem> systemlist = getSysList(s);
		try
		{
			Constructor ctor = system.getDeclaredConstructor(EntityManager.class);
			CoreSystem newSystem = (CoreSystem)ctor.newInstance(em);
			systemlist.add(newSystem);
			if (DEBUG) System.out.println("Added system " + system.getSimpleName());
		} catch (Exception e)
		{
			System.out.println("Kunne ikke opprette system " + system.getSimpleName());
		}
	}

	public void addComponent(State s, Entity entity, Component component)
	{
		EntityManager em = getManager(s);
		em.addComponent(entity, component);

		if (DEBUG) System.out.println("Added component " + component.getClass().getSimpleName() + " to " + entity.name + " in " + s);
	}

	public void runSystems()
	{
		ArrayList<CoreSystem> systemlist = getSysList(state);
		EntityManager em = getManager(state);

		for (CoreSystem sys : systemlist)
		{
			sys.run(em);
		}

		em.removeEntities();
	}

}
