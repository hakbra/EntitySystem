package framework.systems;

import framework.Component;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Followable;
import framework.components.Follower;
import framework.components.Position;
import framework.components.Velocity;
import helpers.Point;


public class FollowerSystem extends CoreSystem{

	public FollowerSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		for (Entity e : em.getAll(Follower.class))
		{
			Point thisPos = em.getComponent(e, Position.class).position;
			Point target = em.getComponent(e, Follower.class).target;
			target = null;
			
			for (Entity e2 : em.get(Followable.class))
			{
				Point newTarget = em.getComponent(e2, Position.class).position;
				
				if (target == null || thisPos.dist(target) > thisPos.dist(newTarget))
					target = newTarget;
			}

			Point thisSpeed = em.getComponent(e, Velocity.class).velocity;
			if (target != null)
			{
				Point speed = target.sub(thisPos);
				thisSpeed.set(speed.norm());
			}
			else
				thisSpeed.set(0, 0);
		}
	}
}
