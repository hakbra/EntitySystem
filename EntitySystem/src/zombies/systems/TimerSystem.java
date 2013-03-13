package zombies.systems;

import framework.CoreSystem;
import framework.CoreEntity;
import framework.World;
import framework.components.Light;
import framework.components.Timer;
import framework.managers.EntityManager;
import framework.utils.Time;

public class TimerSystem extends CoreSystem{
	
	long delayTimer;
	
	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		long now = Time.getTime();
		
		for (CoreEntity e : em.getEntityAll(Timer.class))
		{
			Timer timer = em.getComponent(e, Timer.class);
			if (now - timer.start > timer.time)
			{
				if (timer.type == "destruct")
					em.removeEntity(e);
				else if (timer.type == "selfDestruct")
					em.removeComponent(e, timer);
				else if (timer.type == "light200")
				{
					em.addComponent(e, new Light(200));
					em.addComponent(e, new Timer(15000));
				}
				else
					System.out.println("Unknown timer-type " + timer.type);
			}
		}
	}

	@Override
	public void disable()
	{
		this.enabled = false;
		this.delayTimer = Time.getTime();
	}
	@Override
	public void enable()
	{
		this.enabled = true;
		long delay = Time.getTime() - delayTimer;

		EntityManager em = world.getEntityManager();
		for (CoreEntity e : em.getEntityAll(Timer.class))
		{
			Timer timer = em.getComponent(e, Timer.class);
			timer.start += delay;
		}
	}
}
