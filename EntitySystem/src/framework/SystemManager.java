package framework;

import java.util.ArrayList;
import java.util.HashMap;

public class SystemManager {
	World world;
	public ArrayList<CoreSystem> systems;
	public HashMap<Class, CoreSystem> systemMap = new HashMap<Class, CoreSystem>();
	
	public SystemManager(World w)
	{
		this.world = w;
		systems = new ArrayList<CoreSystem>();
	}

	public void addSystem(CoreSystem cs)
	{
		systems.add(cs);
		systemMap.put(cs.getClass(), cs);
	}

	public void removeSystem(CoreSystem cs)
	{
		systems.remove(cs);
		systemMap.remove(cs.getClass());
	}

	public void toggleSystem(Class c)
	{
		CoreSystem cs = systemMap.get(c);
		cs.enabled = !cs.enabled;
	}

	public void runSystems()
	{
		EntityManager em = world.getEntityManager();
		for (CoreSystem sys : systems)
			if (sys.enabled)
				sys.run(em);
	}
}
