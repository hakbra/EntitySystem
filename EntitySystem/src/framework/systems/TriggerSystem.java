package framework.systems;

import org.lwjgl.input.Keyboard;

import states.CutsceneState;
import states.Level2State;
import states.MessageState;

import helpers.Intersection;
import helpers.Point;
import engine.GLEngine;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.EventListener;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.EmitterOnImpact;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Item;
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
}
