package framework;

import helpers.State;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class SystemManager {
	World world;
	public ArrayList<CoreSystem> systems;
	
	public SystemManager(World w)
	{
		this.world = w;
		systems = new ArrayList<CoreSystem>();
	}

	public void addSystem(CoreSystem cs)
	{
		systems.add(cs);
	}


	public void runSystems()
	{
		EntityManager em = world.getEntityManager();
		for (CoreSystem sys : systems)
			sys.run(em);
	}
}
