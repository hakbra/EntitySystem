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
import framework.components.Hero;
import framework.components.Item;
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
		for (Entity e1 : em.getEntity(Collider.class))
		{
			Point circle1pos = em.getComponent(e1, Position.class).position;
			Circle circle1 = em.getComponent(e1, Circle.class);
			Point s1 = em.getComponent(e1, Velocity.class).velocity;
			Point collision = null;

			for (Entity e2 : em.getEntityAll(Circle.class))
			{
				if (e1 == e2)
					continue;

				Point circle2pos = em.getComponent(e2, Position.class).position;
				Circle circle2 = em.getComponent(e2, Circle.class);
				Point s2  = null;
				Velocity vel2 = em.getComponent(e2, Velocity.class);
				if (vel2 != null)
					s2 = vel2.velocity;

				double moveDist = circle1pos.dist(circle2pos) - circle1.radius - circle2.radius;
				Point dir = circle1pos.sub(circle2pos).norm();

				if (moveDist < 0)
				{
					if (em.hasComponent(e2, Obstacle.class))
					{
						Point line = dir.mult(moveDist);

						int c1 = 1;
						if (s1.len() > 0)
							c1 = 2;

						int c2 = 0;
						if (s2 != null)
							if (s2.len() > 0)
								c2 = 2;
							else
								c2 = 1;

						if (c2 > c1)
							circle2pos.iadd(line);
						else if (c1 > c2)
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
					}
					collision = circle2pos.add(dir.mult(circle2.radius));
					handleCollision(em, e1, e2, collision);
				}
			}

			for (Entity e2 : em.getEntityAll(Polygon.class, Obstacle.class))
			{
				Point polyPos = em.getComponent(e2, Position.class).position;
				Polygon poly = em.getComponent(e2, Polygon.class);

				if (poly.isInside(polyPos, circle1pos))
					collision = circle1pos.sub(s1);

				Point closest = poly.getClosest(polyPos, circle1pos);
				double moveDist = circle1pos.dist(closest) - circle1.radius;
				Point line = null;

				if (poly.isInside(polyPos, circle1pos))
				{
					moveDist += 2*circle1.radius;
					line = closest.sub(circle1pos).norm();
				}
				else if (moveDist < 0)
					line = closest.sub(circle1pos).norm();

				if (line != null)
				{
					circle1pos.iadd(line.mult(moveDist));
					handleCollision(em, e1, e2, closest);
				}
			}
		}
	}

	private void handleCollision(EntityManager em, Entity a, Entity b, Point poi)
	{
		if (em.hasComponent(b, Obstacle.class))
		{
			if (em.hasComponent(a, DestroyOnImpact.class))
				em.removeEntity(a);

			if (em.hasComponent(a, EmitterOnImpact.class))
			{
				Entity emitter = new Entity();
				emitter.name = "emitter";
				em.addComponent(emitter, new Position(poi));
				em.addComponent(emitter, new Timer(0));
				em.addComponent(emitter, new Emitter());
			}
		}
		else if (em.hasComponent(b, Item.class))
		{
			Item item = em.getComponent(b, Item.class);
			if (item.type == "health" && em.hasComponents(a, Health.class, Hero.class))
			{
				Health health = em.getComponent(a, Health.class);
				if (health.current < health.max)
				{
					health.current += item.value;
					if (health.current > health.max)
						health.current = health.max;
					em.removeEntity(b);
				}
					
			}
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
				em.removeEntity(b);

			dam.time = Time.getTime();
		}
	}
}
