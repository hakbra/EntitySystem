package framework.managers;

import java.util.ArrayList;
import java.util.HashMap;

import framework.CoreSystem;
import framework.World;
import framework.enums.StateEnum;

public class SystemManager {
	World world;
	public StateEnum state;
	public ArrayList<CoreSystem> systems;
	public HashMap<Class, CoreSystem> systemMap = new HashMap<Class, CoreSystem>();
	
	public SystemManager(World w, StateEnum s)
	{
		this.world = w;
		this.state = s;
		systems = new ArrayList<CoreSystem>();
	}

	public void addSystem(CoreSystem cs)
	{
		cs.init();
		systems.add(cs);
		systemMap.put(cs.getClass(), cs);
	}

	public void removeSystem(CoreSystem cs)
	{
		cs.stop();
		systems.remove(cs);
		systemMap.remove(cs.getClass());
	}

	public void toggleSystem(Class c)
	{
		CoreSystem cs = systemMap.get(c);
		cs.toggle();
	}
	
	public void disableSystem(Class c)
	{
		CoreSystem cs = systemMap.get(c);
		cs.disable();
	}
	
	public void enableSystem(Class c)
	{
		CoreSystem cs = systemMap.get(c);
		cs.enable();
	}

	public void runSystems()
	{
		EntityManager em = world.getEntityManager();
		for (int i = 0; i < systems.size(); i++)
		{
			CoreSystem sys = systems.get(i);
			if (sys.enabled)
				sys.run();
		}
	}
}
