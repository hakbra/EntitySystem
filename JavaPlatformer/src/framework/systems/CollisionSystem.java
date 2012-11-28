package framework.systems;

import helpers.Point;
import framework.Component;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Bullet;
import framework.components.Circle;
import framework.components.Hero;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Velocity;


public class CollisionSystem extends CoreSystem {

	public CollisionSystem(EntityManager em, Class<? extends Component>... types)
	{
		super(em, types);
	}
	
	@Override
	public void run(EntityManager em)
	{
		for (Entity e1 : entities())
		{
			Point p1 = em.getComponent(e1, Position.class).position;
			float r1 = em.getComponent(e1, Circle.class).radius;
			Point s1 = em.getComponent(e1, Velocity.class).velocity;
			
			for (Entity e2 : em.getAll(Position.class, Circle.class))
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
					
					if (em.hasComponent(e1, Bullet.class) && em.hasComponent(e2, Obstacle.class))
						em.removeLater(e1);
				}
			}
			
			for (Entity e2 : em.getAll(Position.class, Polygon.class))
			{
				Point polyPos = em.getComponent(e2, Position.class).position;
				Polygon poly = em.getComponent(e2, Polygon.class);
				
				if (poly.isInside(polyPos, p1))
					em.removeLater(e1);
				
				for (int i = 0; i < poly.points.size(); i++)
				{
					Point col = p1.pointOnLine(polyPos.add(poly.points.get(i)), polyPos.add(poly.points.get((i+1) % poly.points.size())));
					double d = p1.dist(col);
					Point line = p1.sub(col).norm();
					
					if (d < r1)
					{
						p1.iadd(line.mult(r1 - d));
						if (em.hasComponent(e1, Bullet.class) && em.hasComponent(e2, Obstacle.class))
							em.removeLater(e1);
					}
				}
			}
		}
	}
}
