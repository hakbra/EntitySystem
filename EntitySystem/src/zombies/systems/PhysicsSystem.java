package zombies.systems;


import java.util.Random;

import zombies.components.Acceleration;
import zombies.components.Angle;
import zombies.components.AngleSpeed;
import zombies.components.Position;
import zombies.components.Scale;
import zombies.components.Velocity;
import zombies.utils.Time;

import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.managers.EntityManager;
import framework.utils.Point;

public class PhysicsSystem extends CoreSystem{
	
	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		long now = Time.getTime();

		for (CoreEntity e : em.getEntityAll(Acceleration.class, Velocity.class))
		{
			Point acc 	= 	em.getComponent(e, Acceleration.class).acceleration;
			Point vel 		= em.getComponent(e, Velocity.class).dir;
			vel.iadd(acc);
			acc.set(0, 0);
		}
		Random r = new Random();
		for (CoreEntity e : em.getEntityAll(Position.class, Velocity.class))
		{
			Point position 	= 	em.getComponent(e, Position.class).position;
			Velocity vel 		= em.getComponent(e, Velocity.class);
				
			position.iadd(vel.dir.norm(vel.speed));
		}
		
		for (CoreEntity e : em.getEntityAll(Angle.class, AngleSpeed.class))
		{
			Angle angle 	= 	em.getComponent(e, Angle.class);
			AngleSpeed angleSpeed 		= em.getComponent(e, AngleSpeed.class);
			angle.angle += angleSpeed.speed;
		}
	}
}
