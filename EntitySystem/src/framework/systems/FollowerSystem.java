package framework.systems;

import helpers.Point;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Circle;
import framework.components.Follower;
import framework.components.Pathfinder;
import framework.components.Position;
import framework.components.Velocity;


public class FollowerSystem extends CoreSystem{

	public FollowerSystem(World w)
	{
		super(w);
	}
	
	@Override
	public void run(EntityManager em)
	{
		Entity cam = em.getByStringID("camera");
		Pathfinder pf = em.getComponent(cam, Pathfinder.class);
		
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

			if (em.hasComponents(e, Angle.class, AngleSpeed.class))
			{
				Angle a = em.getComponent(e, Angle.class);
				AngleSpeed as = em.getComponent(e, AngleSpeed.class);
				double oldAng = a.angle;
				double newAng = thisSpeed.angle();
				double delta = thisSpeed.angle( new Point(oldAng));
				
				if (delta > 0)
					as.speed = 1.5;
				else if (delta < 0)
					as.speed = -1.5;
				else
					as.speed = 0;
			}
		}
	}
}
