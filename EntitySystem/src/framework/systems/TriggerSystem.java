package framework.systems;

import org.lwjgl.input.Keyboard;

import helpers.Intersection;
import zombies.states.CutsceneState;
import zombies.states.Level2State;
import zombies.states.MessageState;
import engine.GLEngine;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.EventListener;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.KeyInput;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.components.Trigger;
import framework.components.Velocity;
import framework.enums.EventEnum;
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
	public void action(Intersection i)
	{
		EntityManager em = world.getEntityManager();

		Trigger trigger = em.getComponent(i.b, Trigger.class);
		if (trigger.type == "exit1" && i.inside)
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
		if (trigger.type == "exit2" && i.inside)
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
				System.out.println(i.b.name);
				Gun oldGun = em.getComponent(i.a, Gun.class);
				em.removeComponent(i.a, oldGun);

				Gun newGun = em.getComponent(i.b, Gun.class);
				System.out.println(newGun);
				em.addComponent(i.a, newGun);

				em.addComponent(i.b, oldGun);
				em.addComponent(i.b, new Timer(500, "selfDestruct"));
				em.getComponent(i.b, Tex.class).texture = oldGun.tex;
			}
		}
	}
}
