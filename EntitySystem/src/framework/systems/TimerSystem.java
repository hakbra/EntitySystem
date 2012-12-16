package framework.systems;

import helpers.Time;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Timer;

public class TimerSystem extends CoreSystem{

	public TimerSystem(World w)
	{
		super(w);
	}
	
	@Override
	public void run(EntityManager em)
	{
		long now = Time.getTime();
		
		for (Entity e : em.getEntityAll(Timer.class))
		{
			Timer timer = em.getComponent(e, Timer.class);
			if (now - timer.start > timer.time)
				em.removeEntity(e);
		}
	}
}
