package framework.systems;

import helpers.Point;

import org.lwjgl.input.Keyboard;

import zombies.states.Level2State;
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
import framework.events.Event;
import framework.events.TriggerEvent;
import framework.managers.EntityManager;


public class TriggerSystem extends CoreSystem implements EventListener{


	public TriggerSystem(World w)
	{
		super(w);
	}

	@Override
	public void run(EntityManager em)
	{
	}

	@Override
	public void recieveEvent(Event e)
	{
		TriggerEvent te = (TriggerEvent) e;
		EntityManager em = world.getEntityManager();

		Trigger trigger = em.getComponent(te.trigger, Trigger.class);
		if (trigger.type == "exit1")
		{
			if(em.getComponent(te.hero, Hero.class).parts < 1 && !em.hasComponent(te.trigger, Timer.class))
			{
				Point pos = em.getComponent(te.trigger, Position.class).position;
				System.out.println("New message at " + pos);
				CoreEntity msg = new CoreEntity();
				msg.components.add(new Position(new Point(pos)));
				msg.components.add(new Velocity(new Point(0, 1), 1));
				msg.components.add(new Text("You can't leave empty handed").setLayer(LayerEnum.TEXT));
				msg.components.add(new Timer(1000, "destruct"));
				world.getEntityManager().addEntity(msg);
				
				world.getEntityManager().addComponent(te.trigger, new Timer(10000, "selfDestruct"));
			}
			else if(em.getComponent(te.hero, Hero.class).parts >= 1)
			{
				world.popState();
				Level2State.init(world);

				int j = 0;
				for (CoreEntity h : em.getEntity(Hero.class))
				{
					em.getComponent(h, Position.class).position.set(50, 40 + 80*j);
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
			String name = em.getComponent(te.trigger, GriffPart.class).partName;
			Hero h = em.getComponent(te.hero, Hero.class);
			Point pos = em.getComponent(te.trigger, Position.class).position;
			h.parts++;
			em.removeEntity(te.trigger);

			CoreEntity msg = new CoreEntity();
			msg.components.add(new Position(new Point(pos)));
			msg.components.add(new Velocity(new Point(0, 1), 0.5));
			msg.components.add(new Text("You found (" + name + ")").setLayer(LayerEnum.TEXT));
			msg.components.add(new Timer(1200, "destruct"));
			world.getEntityManager().addEntity(msg);
		}

		if (trigger.type == "health")
		{
			Health health = em.getComponent(te.hero, Health.class);
			if (health.current < health.max)
			{
				health.current += 100;
				if (health.current > health.max)
					health.current = health.max;
				em.removeEntity(te.trigger);
			}

		}
		if (trigger.type == "gun" && !em.hasComponent(te.trigger, Timer.class))
		{
			KeyInput keys = em.getComponent(te.hero, KeyInput.class);

			if (Keyboard.isKeyDown(keys.pickup))
			{
				Gun oldGun = em.getComponent(te.hero, Gun.class);
				em.removeComponent(te.hero, oldGun);

				Gun newGun = em.getComponent(te.trigger, Gun.class);
				em.addComponent(te.hero, newGun);

				em.addComponent(te.trigger, oldGun);
				em.addComponent(te.trigger, new Timer(500, "selfDestruct"));
				em.getComponent(te.trigger, Tex.class).texture = oldGun.tex;
			}
		}
	}
}
