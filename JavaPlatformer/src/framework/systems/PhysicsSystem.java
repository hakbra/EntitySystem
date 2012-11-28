package framework.systems;

import helpers.Point;

import java.util.Date;

import framework.Component;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Position;
import framework.components.Timer;
import framework.components.Velocity;

public class PhysicsSystem extends CoreSystem{
	
	public PhysicsSystem(EntityManager em, Class<? extends Component>... types)
	{
		super(em, types);
	}
	
	@Override
	public void run(EntityManager em)
	{
		long now = new Date().getTime();
		
		for (Entity e : this.entities())
		{
			Point position 	= 	em.getComponent(e, Position.class).position;
			Point vel 		= em.getComponent(e, Velocity.class).velocity;
			position.iadd(vel);
			
			if (em.hasComponent(e, Timer.class))
			{
				Timer timer = em.getComponent(e, Timer.class);
				if (now - timer.start > timer.time)
					em.removeLater(e);
			}
		}
	}
}
