package framework.systems;

import helpers.Time;
import zombies.states.MessageState;
import framework.CoreSystem;
import framework.World;
import framework.components.Bullet;
import framework.components.Damage;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Zombie;
import framework.enums.EventEnum;
import framework.enums.StateEnum;
import framework.events.DamageEvent;
import framework.events.Event;
import framework.managers.EntityManager;


public class DamageSystem extends CoreSystem{


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
	public void recieveEvent(Event e)
	{
		DamageEvent de = (DamageEvent) e;
		EntityManager em = world.getEntityManager();

		if (em.hasComponent(de.attacker, Zombie.class) && em.hasComponent(de.receiver, Zombie.class))
			return;

		Damage dam = em.getComponent(de.attacker, Damage.class);
		Health health = em.getComponent(de.receiver, Health.class);

		if (de.receiver == dam.parent)
			return;

		if (!dam.canDamage())
			return;
		
		if (health.current <= 0)
			return;

		dam.time = Time.getTime();

		health.current -= dam.amount;
		if (health.current <= 0)
		{
			em.removeEntity(de.receiver);

			if (em.hasComponent(de.receiver, Hero.class) && em.getEntity(Hero.class).size() == 1)
			{
				world.popState();
				MessageState.init(world, "GAME OVER");
				world.pushState(StateEnum.MESSAGE);
			}
			else if (em.hasComponent(de.attacker, Bullet.class))
			{
				Bullet bullet = em.getComponent(de.attacker, Bullet.class);
				Hero hero = em.getComponent(bullet.parent, Hero.class);
				hero.kills++;
			}
		}

		dam.time = Time.getTime();

	}
}
