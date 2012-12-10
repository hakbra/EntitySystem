package framework.systems;

import helpers.Point;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
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
		Entity pfEntity = em.getEntity(Pathfinder.class).remove(0);
		Pathfinder pf = em.getComponent(pfEntity, Pathfinder.class);
		
		for (Entity e : em.getEntityAll(Follower.class))
		{
			Point thisPos = em.getComponent(e, Position.class).position;
			Point thisSpeed = em.getComponent(e, Velocity.class).velocity;
			float rad = em.getComponent(e, Circle.class).radius;
			
			Point dir = pf.getDir(thisPos, rad);
			thisSpeed.set(dir);
		}
	}
}
