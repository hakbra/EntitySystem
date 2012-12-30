package framework.systems;

import helpers.Intersection;
import helpers.Point;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.EventListener;
import framework.World;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.EmitterOnImpact;
import framework.components.Position;
import framework.components.Timer;
import framework.enums.EventEnum;
import framework.managers.EntityManager;


public class CollisionSystem extends CoreSystem implements EventListener{


	public CollisionSystem(World w)
	{
		super(w);
		this.event = EventEnum.COLLISION;
	}

	@Override
	public void run(EntityManager em)
	{
	}

	@Override
	public void action(Intersection i)
	{
		EntityManager em = world.getEntityManager();

		if (em.hasComponent(i.a, EmitterOnImpact.class))
		{
			CoreEntity emitter = new CoreEntity();
			emitter.name = "emitter";
			em.addComponent(emitter, new Position(i.poi));
			em.addComponent(emitter, new Timer(0));
			em.addComponent(emitter, new Emitter());
		}

		if (em.hasComponent(i.a, DestroyOnImpact.class))
		{
				em.removeEntity(i.a);
				return;
		}
		
		if (em.hasComponent(i.a, Collider.class))
		{
			Point posA = em.getComponent(i.a, Position.class).position;
			Circle circle = em.getComponent(i.a, Circle.class);

			double dist = posA.dist(i.poi) - circle.radius;
			if (i.inside)
				dist += 2*circle.radius;
			Point mov = posA.sub(i.poi).norm(dist);

			if (em.hasComponent(i.b, Collider.class))
			{
				Point posB = em.getComponent(i.b, Position.class).position;
				int levelA = em.getComponent(i.a, Collider.class).level;
				int levelB = em.getComponent(i.b, Collider.class).level;

				if (levelA > levelB)
					posB.iadd(mov);
				else if (levelA < levelB)
					posA.isub(mov);
				else
				{
					posB.iadd(mov.div(2));
					posA.isub(mov.div(2));
				}
			}
			else
				posA.isub(mov);
		}
	}
}
