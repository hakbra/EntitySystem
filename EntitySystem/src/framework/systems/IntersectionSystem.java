package framework.systems;

import helpers.Intersection;
import helpers.Point;
import helpers.Time;

import org.lwjgl.input.Keyboard;

import states.CutsceneState;
import states.Level2State;
import states.MessageState;
import engine.GLEngine;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.EmitterOnImpact;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Item;
import framework.components.KeyInput;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.components.Trigger;
import framework.components.Velocity;
import framework.components.Zombie;
import framework.enums.EventEnum;
import framework.enums.StateEnum;
import framework.managers.EntityManager;
import framework.managers.EventManager;


public class IntersectionSystem extends CoreSystem {


	public IntersectionSystem(World w)
	{
		super(w);
	}

	@Override
	public void run(EntityManager em)
	{
		for (CoreEntity e1 : em.getEntity(Collider.class))
		{
			Point circle1pos = em.getComponent(e1, Position.class).position;
			Circle circle1 = em.getComponent(e1, Circle.class);

			for (CoreEntity e2 : em.getEntityAll(Circle.class))
			{
				if (e1 == e2)
					continue;

				Circle circle2 = em.getComponent(e2, Circle.class);

				Point col = circle2.getClosest(circle1pos);
				boolean inside = circle2.isInside(circle1pos);

				if (circle1pos.dist(col) < circle1.radius || inside)
					handleCollision( em, new Intersection(e1, e2, col, inside) );
			}

			for (CoreEntity e2 : em.getEntityAll(Polygon.class))
			{
				Polygon poly = em.getComponent(e2, Polygon.class);

				Point col = poly.getClosest(circle1pos);
				boolean inside = poly.isInside(circle1pos);

				if (circle1pos.dist(col) < circle1.radius || inside)
					handleCollision( em, new Intersection(e1, e2, col, inside) );
			}
		}
	}

	private void handleCollision(EntityManager em, Intersection i)
	{
		if (em.hasComponent(i.b, Obstacle.class))
			world.getEventManager().sendEvent(EventEnum.COLLISION, i);

		if (em.hasComponent(i.b, Item.class) && em.hasComponent(i.a, Hero.class))
			world.getEventManager().sendEvent(EventEnum.ITEM, i);
		
		if (em.hasComponent(i.b, Trigger.class) && em.hasComponent(i.a, Hero.class))
			world.getEventManager().sendEvent(EventEnum.TRIGGER, i);

		if (em.hasComponent(i.a, Damage.class) && em.hasComponent(i.b, Health.class))
			world.getEventManager().sendEvent(EventEnum.DAMAGE, i);
	}
}
