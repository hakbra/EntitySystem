package zombies.systems;


import java.util.HashSet;

import zombies.components.Collider;
import zombies.components.CollisionCircle;
import zombies.components.CollisionPolygon;
import zombies.components.Damage;
import zombies.components.Health;
import zombies.components.Hero;
import zombies.components.Obstacle;
import zombies.components.Position;
import zombies.components.Trigger;
import zombies.events.CollisionEvent;
import zombies.events.DamageEvent;
import zombies.events.TriggerEvent;
import zombies.misc.CollisionMap;

import framework.CoreEntity;
import framework.CoreSystem;
import framework.managers.EntityManager;
import framework.utils.Point;


public class IntersectionSystem extends CoreSystem {
	CollisionMap cmap = new CollisionMap();
	CollisionMap pmap = new CollisionMap();

	@Override
	public void run()
	{
		loadCollisionMap();

		EntityManager em = world.getEntityManager();
		for (CoreEntity e1 : em.getEntity(Collider.class))
		{
			Point circle1pos = em.getComponent(e1, Position.class).position;
			CollisionCircle circle1 = em.getComponent(e1, CollisionCircle.class);
			Point radVec = new Point(circle1.radius, circle1.radius);

			HashSet<CoreEntity> cCands = cmap.get(circle1pos.sub(radVec), circle1pos.add(radVec));
			for (CoreEntity e2 : cCands)
			{
				if (e1 == e2)
					continue;

				CollisionCircle circle2 = em.getComponent(e2, CollisionCircle.class);
				
				if (circle2 == null)
					continue;

				Point col = circle2.getClosest(circle1pos);
				boolean inside = circle2.isInside(circle1pos);

				if (circle1pos.dist(col) < circle1.getRadius() || inside)
					handleCollision( em, new CollisionEvent(e1, e2, col, inside) );
			}

			HashSet<CoreEntity> pCands = pmap.get(circle1pos.sub(radVec), circle1pos.add(radVec));
			for (CoreEntity e2 : pCands)
			{
				CollisionPolygon poly = em.getComponent(e2, CollisionPolygon.class);
				
				if (poly == null)
					continue;

				Point col = poly.getClosest(circle1pos);
				boolean inside = poly.isInside(circle1pos);

				if (circle1pos.dist(col) < circle1.getRadius() || inside)
					handleCollision( em, new CollisionEvent(e1, e2, col, inside) );
			}
		}
	}

	private void loadCollisionMap() {
		EntityManager em = world.getEntityManager();
		
		cmap.clear();
		pmap.clear();

		for (CoreEntity e1 : em.getEntityAll(CollisionCircle.class))
		{	
			double rad = em.getComponent(e1, CollisionCircle.class).radius;
			Point pos = em.getComponent(e1, Position.class).position;
			Point radVec = new Point(rad, rad);

			cmap.add(e1, pos.sub(radVec), pos.add(radVec));
		}

		for (CoreEntity e2 : em.getEntityAll(CollisionPolygon.class))
		{
			CollisionPolygon poly = em.getComponent(e2, CollisionPolygon.class);
			Point polyMin = poly.getMin();
			Point polyMax = poly.getMax();

			pmap.add(e2, polyMin, polyMax);
		}
	}

	private void handleCollision(EntityManager em, CollisionEvent ce)
	{
		if (em.hasComponent(ce.obstacle, Obstacle.class))
			world.getEventManager().sendEvent(ce);

		if (em.hasComponent(ce.obstacle, Trigger.class) && em.hasComponent(ce.collider, Hero.class))
			world.getEventManager().sendEvent(new TriggerEvent(ce.collider, ce.obstacle));

		if (em.hasComponent(ce.obstacle, Health.class) && em.hasComponent(ce.collider, Damage.class))
			world.getEventManager().sendEvent(new DamageEvent(ce.collider, ce.obstacle));
	}
}
