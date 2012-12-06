package framework.systems;

import helpers.Point;
import helpers.Time;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Position;
import framework.components.Timer;
import framework.components.Velocity;

public class TimerSystem extends CoreSystem{

	public TimerSystem(EntityManager em)
	{
		super(em);
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
