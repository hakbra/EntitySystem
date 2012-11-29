package framework.systems;

import helpers.Color;
import helpers.Draw;
import helpers.Point;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Bullet;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Timer;
import framework.components.Velocity;


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

				Point s2 = null;
				if (em.hasComponent(e2, Velocity.class))
					s2 = em.getComponent(e2, Velocity.class).velocity;

				Point closest = circle2.getClosest(circle2pos, circle1pos);
				double moveDist = circle1pos.dist(closest) - circle1.radius;
				
				if (moveDist < 0)
				{
					Point line = circle1pos.sub(closest).norm().mult(moveDist);


					int c1 = 1 + (s1.len() > 0 ? 1 : 0);
					int c2 = s2 != null ? 1 : 0;			// 0: no speed, 1: speed = 0, 2: cSpeed > 0
					c2 += s2 != null && s2.len() > 0 ? 1 : 0;

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

					collision = closest;
				}

				if (circle2.isInside(circle2pos, circle1pos))
					collision = circle1pos.sub(s1);
			}
			
			for (Entity e2 : em.getAll(Polygon.class, Obstacle.class))
			{
				Point polyPos = em.getComponent(e2, Position.class).position;
				Polygon poly = em.getComponent(e2, Polygon.class);
				
				if (poly.isInside(polyPos, circle1pos))
					collision = circle1pos.sub(s1);
				
				Point closest = poly.getClosest(polyPos, circle1pos);
				double moveDist = circle1pos.dist(closest) - circle1.radius;
				
				if (moveDist < 0)
				{
					Point line = circle1pos.sub(closest).norm();
					circle1pos.isub(line.mult(moveDist));
					collision = closest;
				}
			}
			
			if (collision != null && em.hasComponent(e1, DestroyOnImpact.class))
			{
				em.removeLater(e1);

				if (em.hasComponent(e1, Bullet.class))
				{
					Entity emitter = new Entity();
					emitter.name = "emitter";
					em.addComponent(emitter, new Position(collision));
					em.addComponent(emitter, new Timer(10));
					em.addComponent(emitter, new Emitter());
				}
			}
		}
	}
}
