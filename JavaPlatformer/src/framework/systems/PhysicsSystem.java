package framework.systems;

import helpers.Point;
import helpers.Time;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Position;
import framework.components.Timer;
import framework.components.Velocity;

public class PhysicsSystem extends CoreSystem{
	
	public PhysicsSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		long now = Time.getTime();
		
		for (Entity e : em.getAll(Position.class, Velocity.class))
		{
			Point position 	= 	em.getComponent(e, Position.class).position;
			Point vel 		= em.getComponent(e, Velocity.class).velocity;
			position.iadd(vel);
			
			if (em.hasComponent(e, Timer.class))
			{
				Timer timer = em.getComponent(e, Timer.class);
				if (now - timer.start > timer.time)
				{
					em.removeLater(e);
				}
			}
		}
	}
}
