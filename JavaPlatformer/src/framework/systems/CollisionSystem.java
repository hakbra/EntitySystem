package framework.systems;

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
			Point p1 = em.getComponent(e1, Position.class).position;
			float r1 = em.getComponent(e1, Circle.class).radius;
			Point s1 = em.getComponent(e1, Velocity.class).velocity;
			Point collision = null;
			
			for (Entity e2 : em.getAll(Circle.class, Obstacle.class))
			{
				Point p2 = em.getComponent(e2, Position.class).position;
				float r2 = em.getComponent(e2, Circle.class).radius;
				
				Point s2 = null;
				if (em.hasComponent(e2, Velocity.class))
					s2 = em.getComponent(e2, Velocity.class).velocity;
				
				if (p1.dist(p2) < r1 + r2)
				{
					Point diff = p1.sub(p2);
					double distDiff = (r1 + r2) - diff.len();
					diff = diff.norm().mult(distDiff);
					
					int c1 = 1 + (s1.len() > 0 ? 1 : 0);
					int c2 = s2 != null ? 1 : 0;		// 0 = no cSpeed, 1 = 0 cSpeed, 2 = cSpeed
					c2 += s2 != null && s2.len() > 0 ? 1 : 0;
					
					if (c2 > c1)
						p2.isub(diff);
					else if (c1 > c2)
						p1.iadd(diff);
					else
					{
						float sum = r1*r1 + r2*r2;
						float p1move = r2*r2 / sum;
						float p2move = r1*r1 / sum;
						p2.isub(diff.mult(p2move));
						p1.iadd(diff.mult(p1move));
					}
					
					collision = p1.add(diff.div(2));
				}
			}
			
			for (Entity e2 : em.getAll(Polygon.class, Obstacle.class))
			{
				Point polyPos = em.getComponent(e2, Position.class).position;
				Polygon poly = em.getComponent(e2, Polygon.class);
				
				if (poly.isInside(polyPos, p1))
					collision = p1.sub(s1);
				
				for (int i = 0; i < poly.points.size(); i++)
				{
					Point col = p1.pointOnLine(polyPos.add(poly.points.get(i)), polyPos.add(poly.points.get((i+1) % poly.points.size())));
					double d = p1.dist(col);
					Point line = p1.sub(col).norm();
					
					if (d < r1)
					{
						p1.iadd(line.mult(r1 - d));
						collision = col;
					}
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
