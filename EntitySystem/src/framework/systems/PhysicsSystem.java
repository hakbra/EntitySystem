package framework.systems;

import helpers.Point;
import helpers.Time;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Position;
import framework.components.Velocity;

public class PhysicsSystem extends CoreSystem{
	
	public PhysicsSystem(World w)
	{
		super(w);
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
		}
		
		for (Entity e : em.getEntityAll(Angle.class, AngleSpeed.class))
		{
			Angle angle 	= 	em.getComponent(e, Angle.class);
			AngleSpeed angleSpeed 		= em.getComponent(e, AngleSpeed.class);
			angle.angle += angleSpeed.speed;
		}
	}
}
