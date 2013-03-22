package framework;


import java.util.HashMap;
import java.util.Map;

import framework.managers.DataManager;
import framework.managers.EntityManager;
import framework.managers.EventManager;
import framework.managers.SystemManager;
import framework.utils.Point;


public class World {
	private int state;
	
	private Map<Integer, EntityManager> eManagers;
	private Map<Integer, SystemManager> sManagers;
	private Map<Integer, DataManager> dManagers;
	private Map<Integer, EventManager> evManagers;
	
	public Point camera;
	public Point mapdim;

	public World()
	{
		this.state = DynEnum.at("state").get("null");

		this.dManagers = new HashMap<>();
		this.sManagers = new HashMap<>();
		this.eManagers = new HashMap<>();
		this.evManagers = new HashMap<>();
		
		camera = new Point();
		mapdim = new Point();
	}

	public boolean run()
	{
		return state != DynEnum.at("state").get("exit");
	}

	public void setStateAndClear(int s)
	{
		this.state = s;
		this.clearState();
	}
	public void setState(int s)
	{
		this.state = s;
	}

	private EntityManager getEntityManager(int s)
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

	private SystemManager getSystemManager(int s)
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

	private DataManager getDataManager(int s)
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

	private EventManager getEventManager(int s)
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
