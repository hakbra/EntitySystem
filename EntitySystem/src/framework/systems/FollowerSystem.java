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
			Follower follower = em.getComponent(e, Follower.class);
			Point pos = em.getComponent(e, Position.class).position;
			Point vel = em.getComponent(e, Velocity.class).velocity;
			
			Point dir = pf.getDir(pos, follower.limit);
			vel.set(dir);
			
			if (follower.limit > 0 && dir.len() > 0)
				follower.limit = -1;

			if (em.hasComponents(e, Angle.class, AngleSpeed.class))
			{
				
				Angle a = em.getComponent(e, Angle.class);
				AngleSpeed as = em.getComponent(e, AngleSpeed.class);
				double oldAng = a.angle;
				double newAng = vel.angle();
				double delta = vel.angle( new Point(oldAng));
				
				double m = Math.abs(vel.norm().dot( new Point(oldAng).norm() ));
				
				vel.imult(m*m*m);
				
				if (delta > 0)
					as.speed = 3;
				else if (delta < 0)
					as.speed = -3;
				else
					as.speed = 0;
			}
		}
	}
}
