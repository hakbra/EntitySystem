package framework.systems;

import interfaces.EventListener;
import helpers.Point;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Collider;
import framework.components.CollisionCircle;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.EmitterOnImpact;
import framework.components.Position;
import framework.components.Timer;
import framework.enums.EventEnum;
import framework.events.CollisionEvent;
import framework.events.Event;
import framework.managers.EntityManager;


public class CollisionSystem extends CoreSystem implements EventListener{


	@Override
	public void run(EntityManager em)
	{
	}

	@Override
	public void recieveEvent(Event e)
	{
		CollisionEvent ce = (CollisionEvent) e;
		EntityManager em = world.getEntityManager();

		if (em.hasComponent(ce.collider, EmitterOnImpact.class))
		{
			CoreEntity emitter = new CoreEntity();
			emitter.name = "emitter";
			em.addComponent(emitter, new Position(ce.pointOfImpact));
			em.addComponent(emitter, new Timer(0));
			em.addComponent(emitter, new Emitter());
		}

		if (em.hasComponent(ce.collider, DestroyOnImpact.class))
		{
			em.removeEntity(ce.collider);
			return;
		}

		if (em.hasComponent(ce.collider, Collider.class))
		{
			Point posA = em.getComponent(ce.collider, Position.class).position;
			CollisionCircle circle = em.getComponent(ce.collider, CollisionCircle.class);

			double dist = posA.dist(ce.pointOfImpact) - circle.getRadius();
			if (ce.inside)
				dist += 2*circle.getRadius();
			Point mov = posA.sub(ce.pointOfImpact).norm(dist);

			if (em.hasComponent(ce.obstacle, Collider.class))
			{
				Point posB = em.getComponent(ce.obstacle, Position.class).position;
				int levelA = em.getComponent(ce.collider, Collider.class).level;
				int levelB = em.getComponent(ce.obstacle, Collider.class).level;

				if (levelA > levelB)
					posB.iadd(mov);
				else if (levelA < levelB)
					posA.isub(mov);
				else
				{
					CollisionCircle circle2 = em.getComponent(ce.obstacle, CollisionCircle.class);
					double r1 = circle.getRadius();
					double r2 = circle2.getRadius();
					double ratio = r1*r1 / (r1*r1+r2*r2);
					posB.iadd(mov.mult(ratio));
					posA.isub(mov.mult(1-ratio));
				}
			}
			else
				posA.isub(mov);
		}
	}
}
