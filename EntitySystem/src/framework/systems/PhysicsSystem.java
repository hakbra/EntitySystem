package framework.systems;

import helpers.Point;
import helpers.Time;

import java.util.Random;

import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Acceleration;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Position;
import framework.components.Scale;
import framework.components.Velocity;
import framework.managers.EntityManager;

public class PhysicsSystem extends CoreSystem{
	
	public PhysicsSystem(World w)
	{
		super(w);
	}
	
	@Override
	public void run(EntityManager em)
	{
		long now = Time.getTime();

		for (CoreEntity e : em.getEntityAll(Acceleration.class, Velocity.class))
		{
			Point acc 	= 	em.getComponent(e, Acceleration.class).acceleration;
			Point vel 		= em.getComponent(e, Velocity.class).velocity;
			vel.iadd(acc);
			acc.set(0, 0);
		}
		Random r = new Random();
		for (CoreEntity e : em.getEntityAll(Position.class, Velocity.class))
		{
			Point position 	= 	em.getComponent(e, Position.class).position;
			Point vel 		= em.getComponent(e, Velocity.class).velocity;
				
			position.iadd(vel);
		}
		
		for (CoreEntity e : em.getEntityAll(Angle.class, AngleSpeed.class))
		{
			Angle angle 	= 	em.getComponent(e, Angle.class);
			AngleSpeed angleSpeed 		= em.getComponent(e, AngleSpeed.class);
			angle.angle += angleSpeed.speed;
		}
	}
}
