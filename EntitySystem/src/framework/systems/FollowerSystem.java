package framework.systems;

import helpers.Point;
import framework.CoreSystem;
import framework.CoreEntity;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.CollisionCircle;
import framework.components.Follower;
import framework.components.Pathfinder;
import framework.components.Position;
import framework.components.Velocity;
import framework.managers.EntityManager;


public class FollowerSystem extends CoreSystem{

	public FollowerSystem(World w)
	{
		super(w);
	}
	
	@Override
	public void run(EntityManager em)
	{
		CoreEntity path = em.getByStringID("path");
		Pathfinder pf = em.getComponent(path, Pathfinder.class);
		
		for (CoreEntity e : em.getEntityAll(Follower.class))
		{
			Follower follower = em.getComponent(e, Follower.class);
			Point pos = em.getComponent(e, Position.class).position;
			Point vel = em.getComponent(e, Velocity.class).dir;
			
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
