package framework;



import java.util.HashMap;
import java.util.Stack;

import framework.enums.EventEnum;
import framework.enums.StateEnum;
import framework.managers.DataManager;
import framework.managers.EntityManager;
import framework.managers.EventManager;
import framework.managers.SystemManager;
import framework.utils.Point;


public class World {
	private StateEnum state;
	
	private HashMap<StateEnum, EntityManager> eManagers;
	private HashMap<StateEnum, SystemManager> sManagers;
	private HashMap<StateEnum, DataManager> dManagers;
	private HashMap<StateEnum, EventManager> evManagers;
	
	public Point camera;
	public Point mapdim;

	public World()
	{
		this.state = StateEnum.NULL;

		this.dManagers = new HashMap<StateEnum, DataManager>();
		this.sManagers = new HashMap<StateEnum, SystemManager>();
		this.eManagers = new HashMap<StateEnum, EntityManager>();
		this.evManagers = new HashMap<StateEnum, EventManager>();
		
		camera = new Point();
		mapdim = new Point();
	}

	public boolean run()
	{
		return state != StateEnum.EXIT;
	}

	public void setStateAndClear(StateEnum s)
	{
		this.state = s;
		this.clearState();
	}
	public void setState(StateEnum s)
	{
		this.state = s;
	}

	private EntityManager getEntityManager(StateEnum s)
	{
		EntityManager em = eManagers.get(s);
		if (em == null)
		{
			em = new EntityManager(this, s);
			eManagers.put(s, em);
		}
		return em;
	}

	public EntityManager getEntityManager()
	{
		return getEntityManager(state);
	}

	private SystemManager getSystemManager(StateEnum s)
	{
		SystemManager sm = sManagers.get(s);
		if (sm == null)
		{
			sm = new SystemManager(this, s);
			sManagers.put(s, sm);
		}
		return sm;
	}

	public SystemManager getSystemManager()
	{
		return getSystemManager(state);
	}

	private DataManager getDataManager(StateEnum s)
	{
		DataManager dm = dManagers.get(s);
		if (dm == null)
		{
			dm = new DataManager(this);
			dManagers.put(s, dm);
		}
		return dm;
	}

	public DataManager getDataManager()
	{
		return getDataManager(state);
	}

	private EventManager getEventManager(StateEnum s)
	{
		EventManager em = evManagers.get(s);
		if (em == null)
		{
			em = new EventManager(this);
			evManagers.put(s, em);
		}
		return em;
	}

	public EventManager getEventManager()
	{
		return getEventManager(state);
	}
	
	public void addEntity(CoreEntity e)
	{
		getEntityManager(state).addEntity(e);
		e.world = this;
	}
	
	public void registerID(CoreEntity e)
	{
		getEntityManager(state).addStringID(e);
	}
	
	public CoreSystem addSystem(CoreSystem cs)
	{
		cs.world = this;
		getSystemManager(state).addSystem(cs);
		return cs;
	}

	public void runSystems() {
		getSystemManager().runSystems();
		getEntityManager().removeEntities();
	}
	
	private void clearState()
	{
		eManagers.remove(state);
		sManagers.remove(state);
		dManagers.remove(state);
		evManagers.remove(state);
	}
}
