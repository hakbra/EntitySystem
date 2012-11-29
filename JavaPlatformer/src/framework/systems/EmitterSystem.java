package framework.systems;

import helpers.Color;
import helpers.Point;
import helpers.Time;

import java.util.Random;

import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.Particle;
import framework.components.Position;
import framework.components.Timer;
import framework.components.Velocity;

public class EmitterSystem extends CoreSystem{

	public EmitterSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		for (Entity e : em.get(Emitter.class))
		{
			Point position 	= 	em.getComponent(e, Position.class).position;
			
			for (int i = 0; i < 4; i++)
			{
				Random r = new Random();
				float angle = r.nextInt() % 360;
				float speed = 2 + r.nextFloat() * 2;
				Color c = new Color(1, r.nextFloat(), 0);
				
				Entity particle = new Entity();
				particle.name = "particle";
				em.addComponent(particle, new Position(new Point(position)));
				em.addComponent(particle, new Velocity(new Point(angle).mult(speed)));
				em.addComponent(particle, new Circle(3, c));
				em.addComponent(particle, new Timer(70 + r.nextInt(50)));
				em.addComponent(particle, new Particle());
				em.addComponent(particle, new Collider());
				em.addComponent(particle, new DestroyOnImpact());
			}
			
			Timer timer = em.getComponent(e, Timer.class);
			if (Time.getTime() - timer.start > timer.time)
				em.removeLater(e);
		}

		for (Entity e : em.get(Particle.class))
		{
			Color c 	= 	em.getComponent(e, Circle.class).color;
			Timer timer = 	em.getComponent(e, Timer.class);
			
			float elapsed = (Time.getTime() - timer.start);
			float full = timer.time;
			
			c.mult(Math.pow(1 - elapsed/full, 2/3));
			
		}
	}
}
