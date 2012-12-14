package framework.systems;

import helpers.Point;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Circle;
import framework.components.Follower;
import framework.components.Pathfinder;
import framework.components.Position;
import framework.components.Velocity;
import framework.components.Zombie;


public class FollowerSystem extends CoreSystem{

	public FollowerSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		Entity world = em.getByStringID("world");
		Pathfinder pf = em.getComponent(world, Pathfinder.class);
		Point worldPos = em.getComponent(world, Position.class).position;
		
		for (Entity e : em.getEntityAll(Follower.class))
		{
			Point thisPos = em.getComponent(e, Position.class).position;
			Follower follower = em.getComponent(e, Follower.class);
			Point thisSpeed = em.getComponent(e, Velocity.class).velocity;
			float rad = em.getComponent(e, Circle.class).radius;
			
			Point dir = pf.getDir(thisPos, follower.limit);
			thisSpeed.set(dir);
			
			if (follower.limit > 0 && dir.len() > 0)
				follower.limit = -1;

			if (em.hasComponent(e, Angle.class))
			{
				Angle a = em.getComponent(e, Angle.class);
				//a.angle = (float) thisSpeed.norm().add( new Point(a.angle)).angle();
			}
		}
	}
}
