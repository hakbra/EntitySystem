package framework.systems;

import helpers.Point;
import helpers.Time;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Position;
import framework.components.Velocity;
import framework.components.Zombie;

public class PhysicsSystem extends CoreSystem{
	
	public PhysicsSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		long now = Time.getTime();
		
		for (Entity e : em.getEntityAll(Position.class, Velocity.class))
		{
			Point position 	= 	em.getComponent(e, Position.class).position;
			Point vel 		= em.getComponent(e, Velocity.class).velocity;
			position.iadd(vel);
			
			if (em.hasComponent(e, Zombie.class))
			{
				em.getComponent(e, Angle.class).angle = (float) vel.angle();
			}
		}
	}
}
