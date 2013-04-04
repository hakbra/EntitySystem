package zombies.systems;


import java.util.HashSet;

import zombies.components.Collider;
import zombies.components.CollisionCircle;
import zombies.components.CollisionComponent;
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
	CollisionMap collisionMap = new CollisionMap();

	@Override
	public void run()
	{
		loadCollisionMap();

		EntityManager em = world.getEntityManager();
		for (CoreEntity e1 : em.getEntity(Collider.class))
		{
			Point pos1 = em.getComponent(e1, Position.class).position;
			CollisionCircle circle1 = em.getComponent(e1, CollisionCircle.class);

			HashSet<CoreEntity> cCands = collisionMap.get(pos1.add(circle1.getMin()), pos1.add(circle1.getMax()));
			for (CoreEntity e2 : cCands)
			{
				if (e1 == e2)
					continue;

				CollisionComponent col2 = em.getComponent(e2, CollisionComponent.class);
				
				if (col2 == null)
				{
					System.out.println("is null");
					continue;
				}

				Point col = col2.getClosest(pos1);
				boolean inside = col2.isInside(pos1);

				if (pos1.dist(col) < circle1.getRadius() || inside)
					handleCollision( em, new CollisionEvent(e1, e2, col, inside) );
			}
		}
	}

	private void loadCollisionMap() {
		EntityManager em = world.getEntityManager();
		
		collisionMap.clear();

		for (CoreEntity e1 : em.getEntityAll(CollisionComponent.class))
		{	
			CollisionComponent col = em.getComponent(e1, CollisionComponent.class);
			Point pos = em.getComponent(e1, Position.class).position;

			collisionMap.add(e1, pos.add(col.getMin()), pos.add(col.getMax()));
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
