package framework.systems;

import org.lwjgl.input.Keyboard;

import states.CutsceneState;
import states.Level2State;
import states.MessageState;

import helpers.Intersection;
import helpers.Point;
import helpers.Time;
import engine.GLEngine;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.EventListener;
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
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.components.Trigger;
import framework.components.Velocity;
import framework.components.Zombie;
import framework.enums.EventEnum;
import framework.enums.StateEnum;
import framework.managers.EntityManager;


public class DamageSystem extends CoreSystem implements EventListener{


	public DamageSystem(World w)
	{
		super(w);
		this.event = EventEnum.DAMAGE;
	}

	@Override
	public void run(EntityManager em)
	{
	}

	@Override
	public void action(Intersection i)
	{
		EntityManager em = world.getEntityManager();

		if (em.hasComponent(i.a, Zombie.class) && em.hasComponent(i.b, Zombie.class))
			return;

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
