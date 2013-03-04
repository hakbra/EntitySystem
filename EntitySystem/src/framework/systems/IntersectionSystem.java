package framework.systems;

import helpers.Point;

import java.util.HashSet;

import framework.CoreEntity;
import framework.CoreSystem;
import framework.components.Button;
import framework.components.Collider;
import framework.components.CollisionCircle;
import framework.components.CollisionPolygon;
import framework.components.Damage;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Obstacle;
import framework.components.Position;
import framework.components.Trigger;
import framework.events.CollisionEvent;
import framework.events.DamageEvent;
import framework.events.TriggerEvent;
import framework.managers.EntityManager;
import framework.misc.CollisionMap;


public class IntersectionSystem extends CoreSystem {
	CollisionMap cmap = new CollisionMap();

	@Override
	public void run()
	{
		loadCollisionMap();

		EntityManager em = world.getEntityManager();
		for (CoreEntity e1 : em.getEntity(Collider.class))
		{
			Point circle1pos = em.getComponent(e1, Position.class).position;
			CollisionCircle circle1 = em.getComponent(e1, CollisionCircle.class);

			HashSet<CoreEntity> cCands = cmap.getCircleCandidates(circle1pos, circle1.radius);
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

			HashSet<CoreEntity> pCands = cmap.getPolyCandidates(circle1pos, circle1.radius);
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

		for (CoreEntity e1 : em.getEntityAll(CollisionCircle.class))
		{	
			CollisionCircle circle = em.getComponent(e1, CollisionCircle.class);
			Point pos = em.getComponent(e1, Position.class).position;

			cmap.addCircle(e1, pos, circle.radius);
		}

		for (CoreEntity e2 : em.getEntityAll(CollisionPolygon.class))
		{
			CollisionPolygon poly = em.getComponent(e2, CollisionPolygon.class);
			Point pos = em.getComponent(e2, Position.class).position;
			Point polyMin = poly.min.add(pos);
			Point polyMax = poly.max.add(pos);

			cmap.addPolygon(e2, polyMin, polyMax);
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
