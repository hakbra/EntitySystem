package zombies.systems;


import org.lwjgl.input.Keyboard;

import zombies.components.GriffPart;
import zombies.components.Gun;
import zombies.components.Health;
import zombies.components.Hero;
import zombies.components.KeyInput;
import zombies.components.Position;
import zombies.components.Tex;
import zombies.components.Text;
import zombies.components.Timer;
import zombies.components.Trigger;
import zombies.components.Velocity;
import zombies.events.StatusEvent;
import zombies.events.TriggerEvent;
import zombies.states.Level2State;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.DynEnum;
import framework.events.Event;
import framework.interfaces.EventListener;
import framework.managers.EntityManager;
import framework.managers.EventManager;
import framework.utils.Point;


public class TriggerSystem extends CoreSystem implements EventListener{

	@Override
	public void init ()
	{
		EventManager em = world.getEventManager();
		em.addListener(DynEnum.at("event").get("trigger"), this);
	}

	@Override
	public void run()
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
				CoreEntity msg = new CoreEntity();
				msg.components.add(new Position(new Point(pos)));
				msg.components.add(new Velocity(new Point(0, 1), 1));
				msg.components.add(new Text("You can't leave empty handed").setLayer(DynEnum.at("layer").get("text")));
				msg.components.add(new Timer(1000, "destruct"));
				world.getEntityManager().addEntity(msg);
				
				world.getEntityManager().addComponent(te.trigger, new Timer(10000, "selfDestruct"));
			}
			else if(em.getComponent(te.hero, Hero.class).parts >= 1)
			{
				world.setState(DynEnum.at("state").get("level2"));
				Level2State.init(world);
				world.getEventManager().sendEvent(new StatusEvent(te.hero.name + " has descended"));
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
			msg.components.add(new Text("You found (" + name + ")").setLayer(DynEnum.at("layer").get("text")));
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

				world.getEventManager().sendEvent(new StatusEvent(te.hero.name + " picked up a gun"));
			}
		}
	}
}
