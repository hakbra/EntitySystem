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

		else if (em.hasComponent(i.b, Item.class) && em.hasComponent(i.a, Hero.class))
		{
			Item item = em.getComponent(i.b, Item.class);
			if (item.type == "health")
			{
				Health health = em.getComponent(i.a, Health.class);
				if (health.current < health.max)
				{
					health.current += item.value;
					if (health.current > health.max)
						health.current = health.max;
					em.removeEntity(i.b);
				}

			}
			if (item.type == "gun" && !em.hasComponent(i.b, Timer.class))
			{
				KeyInput keys = em.getComponent(i.a, KeyInput.class);

				if (Keyboard.isKeyDown(keys.pickup))
				{
					Gun oldGun = em.getComponent(i.a, Gun.class);
					em.removeComponent(i.a, oldGun);

					Gun newGun = em.getComponent(i.b, Gun.class);
					em.addComponent(i.a, newGun);

					em.addComponent(i.b, oldGun);
					em.addComponent(i.b, new Timer(500, "selfDestruct"));
					em.getComponent(i.b, Tex.class).texture = oldGun.tex;
				}
			}
		}
		else if (em.hasComponent(i.b, Trigger.class) && em.hasComponent(i.a, Hero.class))
		{
			Trigger trigger = em.getComponent(i.b, Trigger.class);
			if (trigger.type == "exit1")
			{
				world.popState();
				CutsceneState.init(world);
				
				int j = 0;
				for (CoreEntity h : em.getEntity(Hero.class))
				{
					em.getComponent(h, Position.class).position.set(GLEngine.WIDTH - 50, GLEngine.HEIGHT / 2 - 40 + 80*j);
					em.getComponent(h, Velocity.class).velocity.set(-4, 0);
					em.getComponent(h, Angle.class).angle = 180.0;
					em.getComponent(h, AngleSpeed.class).speed = 0.0;
					world.addEntity(h, StateEnum.CUTSCENE);
					j++;
				}
				world.pushState(StateEnum.CUTSCENE);
			}
			if (trigger.type == "exit2")
			{
				world.popState();
				Level2State.init(world);
				int j = 0;
				for (CoreEntity h : em.getEntity(Hero.class))
				{
					em.getComponent(h, Position.class).position.set(GLEngine.WIDTH - 50, GLEngine.HEIGHT / 2 - 40 + 80*j);
					em.getComponent(h, Velocity.class).velocity.set(0, 0);
					em.getComponent(h, Angle.class).angle = 180.0;
					em.getComponent(h, AngleSpeed.class).speed = 0.0;
					world.addEntity(h, StateEnum.LEVEL2);
					j++;
				}
				world.pushState(StateEnum.LEVEL2);
				MessageState.init(world, "YOU WON");
				world.pushState(StateEnum.MESSAGE);
			}
		}

		if (em.hasComponent(i.a, Zombie.class) && em.hasComponent(i.b, Zombie.class))
			return;

		if (em.hasComponent(i.a, Damage.class) && em.hasComponent(i.b, Health.class))
		{
			Damage dam = em.getComponent(i.a, Damage.class);
			Health health = em.getComponent(i.b, Health.class);

			if (i.b == dam.parent)
				return;

			if (!dam.canDamage())
				return;

			dam.time = Time.getTime();

			health.current -= dam.amount;
			if (health.current <= 0)
			{
				em.removeEntity(i.b);

				if (em.hasComponent(i.b, Hero.class) && em.getEntity(Hero.class).size() == 1)
				{
					world.popState();
					MessageState.init(world, "GAME OVER");
					world.pushState(StateEnum.MESSAGE);
				}
			}

			dam.time = Time.getTime();

		}
	}
}
