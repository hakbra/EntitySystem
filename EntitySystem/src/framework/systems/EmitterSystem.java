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
		for (Entity e : em.getEntity(Emitter.class))
		{
			Point position 	= 	em.getComponent(e, Position.class).position;
			
			for (int i = 0; i < 4; i++)
			{
				Random r = new Random();
				
				Color c = new Color(1, r.nextFloat(), 0);
				float angle = r.nextInt() % 360;
				float speed =  r.nextFloat() * 4;
				int size = 3 + r.nextInt(2);
				int time = 150 + r.nextInt(100);
				
				Entity particle = new Entity();
				particle.name = "particle";
				em.addComponent(particle, new Position(new Point(position)));
				em.addComponent(particle, new Velocity(new Point(angle).mult(speed)));
				em.addComponent(particle, new Circle(size, c));
				em.addComponent(particle, new Timer(time));
				em.addComponent(particle, new Particle());
				em.addComponent(particle, new Collider());
			}
		}

		for (Entity e : em.getEntity(Particle.class))
		{
			Circle circ 	= 	em.getComponent(e, Circle.class);
			Timer timer = 	em.getComponent(e, Timer.class);
			
			float elapsed = (Time.getTime() - timer.start);
			float full = timer.time;

			circ.color.alpha = (float) (1 - elapsed/full);
		}
	}
}
