package framework.systems;

import helpers.Point;
import helpers.Time;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.EmitterOnImpact;
import framework.components.Health;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Timer;
import framework.components.Velocity;
import framework.components.Zombie;


public class CollisionSystem extends CoreSystem {

	public CollisionSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		for (Entity e1 : em.get(Collider.class))
		{
			Point circle1pos = em.getComponent(e1, Position.class).position;
			Circle circle1 = em.getComponent(e1, Circle.class);
			Point s1 = em.getComponent(e1, Velocity.class).velocity;
			Point collision = null;
			
			for (Entity e2 : em.getAll(Circle.class, Obstacle.class))
			{
				if (e1 == e2)
					continue;
				
				Point circle2pos = em.getComponent(e2, Position.class).position;
				Circle circle2 = em.getComponent(e2, Circle.class);

				double moveDist = circle1pos.dist(circle2pos) - circle1.radius - circle2.radius;
				Point dir = circle1pos.sub(circle2pos).norm();
				
				if (moveDist < 0)
				{
					Point line = dir.mult(moveDist);

					if (!em.hasComponent(e2, Velocity.class) || em.hasComponent(e1, Zombie.class))
						circle1pos.isub(line);
					else
					{
						float r1squared = circle1.radius * circle1.radius;
						float r2squared = circle2.radius * circle2.radius;
						float sum = r1squared + r2squared;
						float p1move = r2squared / sum;
						float p2move = r1squared / sum;
						circle2pos.iadd(line.mult(p2move));
						circle1pos.isub(line.mult(p1move));
					}

					collision = circle2pos.add(dir.mult(circle2.radius));
					handleCollision(em, e1, e2, collision);
				}
			}
			
			for (Entity e2 : em.getAll(Polygon.class, Obstacle.class))
			{
				Point polyPos = em.getComponent(e2, Position.class).position;
				Polygon poly = em.getComponent(e2, Polygon.class);
				
				if (poly.isInside(polyPos, circle1pos))
					collision = circle1pos.sub(s1);
				
				Point closest = poly.getClosest(polyPos, circle1pos);
				double moveDist = circle1pos.dist(closest) - circle1.radius;
				if (poly.isInside(polyPos, circle1pos))
				{
					moveDist *= -1;
					moveDist += circle1.radius*2;
				}
				
				if (moveDist < 0)
				{
					Point line = circle1pos.sub(closest).norm();
					circle1pos.isub(line.mult(moveDist));
					handleCollision(em, e1, e2, closest);
				}
			}
		}
	}
	
	private void handleCollision(EntityManager em, Entity a, Entity b, Point poi)
	{
		if (em.hasComponent(a, DestroyOnImpact.class))
			em.removeLater(a);

		if (em.hasComponent(a, EmitterOnImpact.class))
		{
			Entity emitter = new Entity();
			emitter.name = "emitter";
			em.addComponent(emitter, new Position(poi));
			em.addComponent(emitter, new Timer(0));
			em.addComponent(emitter, new Emitter());
		}

		if (em.hasComponent(a, Zombie.class) && em.hasComponent(b, Zombie.class))
			return;

		if (em.hasComponent(a, Damage.class) && em.hasComponent(b, Health.class))
		{
			Damage dam = em.getComponent(a, Damage.class);
			Health health = em.getComponent(b, Health.class);
			
			if (b == dam.parent)
				return;
			
			if (!dam.canDamage())
				return;
			
			dam.time = Time.getTime();
			
			health.current -= dam.amount;
			if (health.current <= 0)
				em.removeLater(b);

			dam.time = Time.getTime();
		}
	}
}
