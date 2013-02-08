package framework.systems;

import helpers.Data;
import helpers.Point;

import org.lwjgl.input.Keyboard;

import zombies.states.Level2State;
import engine.GLEngine;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.EventListener;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.GriffPart;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.KeyInput;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Text;
import framework.components.Timer;
import framework.components.Trigger;
import framework.components.Velocity;
import framework.enums.EventEnum;
import framework.enums.LayerEnum;
import framework.enums.StateEnum;
import framework.managers.EntityManager;


public class TriggerSystem extends CoreSystem implements EventListener{


	public TriggerSystem(World w)
	{
		super(w);
		this.event = EventEnum.TRIGGER;
	}

	@Override
	public void run(EntityManager em)
	{
	}

	@Override
	public void recieveEvent(Data i)
	{
		EntityManager em = world.getEntityManager();

		Trigger trigger = em.getComponent(i.b, Trigger.class);
		if (trigger.type == "exit1" && i.inside)
		{
			if(em.getComponent(i.a, Hero.class).parts < 1 && !em.hasComponent(i.b, Timer.class))
			{
				Point pos = em.getComponent(i.b, Position.class).position;
				System.out.println("New message at " + pos);
				CoreEntity msg = new CoreEntity();
				msg.components.add(new Position(new Point(pos)));
				msg.components.add(new Velocity(new Point(0, 1), 1));
				msg.components.add(new Text("You can't leave empty handed").setLayer(LayerEnum.TEXT));
				msg.components.add(new Timer(1000, "destruct"));
				world.getEntityManager().addEntity(msg);
				
				world.getEntityManager().addComponent(i.b, new Timer(10000, "selfDestruct"));
			}
			else if(em.getComponent(i.a, Hero.class).parts >= 1)
			{
				world.popState();
				Level2State.init(world);

				int j = 0;
				for (CoreEntity h : em.getEntity(Hero.class))
				{
					em.getComponent(h, Position.class).position.set(world.WIDTH - 50, world.HEIGHT / 2 - 40 + 80*j);
					em.getComponent(h, Velocity.class).dir.set(-4, 0);
					em.getComponent(h, Angle.class).angle = 180.0;
					em.getComponent(h, AngleSpeed.class).speed = 0.0;
					world.addEntity(h, StateEnum.CUTSCENE);
					j++;
				}
				world.pushState(StateEnum.LEVEL2);
			}
		}

		if (trigger.type == "griff")
		{
			String name = em.getComponent(i.b, GriffPart.class).partName;
			Hero h = em.getComponent(i.a, Hero.class);
			Point pos = em.getComponent(i.b, Position.class).position;
			h.parts++;
			em.removeEntity(i.b);

			CoreEntity msg = new CoreEntity();
			msg.components.add(new Position(new Point(pos)));
			msg.components.add(new Velocity(new Point(0, 1), 0.5));
			msg.components.add(new Text("You found (" + name + ")").setLayer(LayerEnum.TEXT));
			msg.components.add(new Timer(1200, "destruct"));
			world.getEntityManager().addEntity(msg);
		}

		if (trigger.type == "health")
		{
			Health health = em.getComponent(i.a, Health.class);
			if (health.current < health.max)
			{
				health.current += 100;
				if (health.current > health.max)
					health.current = health.max;
				em.removeEntity(i.b);
			}

		}
		if (trigger.type == "gun" && !em.hasComponent(i.b, Timer.class))
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
}
