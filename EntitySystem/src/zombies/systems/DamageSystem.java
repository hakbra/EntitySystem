package zombies.systems;

import zombies.components.Bullet;
import zombies.components.Damage;
import zombies.components.Health;
import zombies.components.Hero;
import zombies.components.Zombie;
import framework.CoreSystem;
import framework.World;
import framework.enums.EventEnum;
import framework.enums.StateEnum;
import framework.events.DamageEvent;
import framework.events.Event;
import framework.events.StatusEvent;
import framework.interfaces.EventListener;
import framework.managers.EntityManager;
import framework.managers.EventManager;
import framework.utils.Time;


public class DamageSystem extends CoreSystem implements EventListener{

	@Override
	public void init ()
	{
		EventManager em = world.getEventManager();
		em.addListener(EventEnum.DAMAGE, this);
	}

	@Override
	public void run()
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
			
			String attackerName = "";
			if (em.hasComponent(de.attacker, Bullet.class))
				attackerName = em.getComponent(de.attacker, Bullet.class).owner.name;
			else
				attackerName = de.attacker.name;
			
			world.getEventManager().sendEvent(new StatusEvent(attackerName + " killed " + de.receiver.name));
			
			if (em.hasComponent(de.receiver, Hero.class) && em.getEntity(Hero.class).size() == 1)
			{
				world.setState(StateEnum.START_MENU);
			}
			else if (em.hasComponent(de.attacker, Bullet.class))
			{
				Bullet bullet = em.getComponent(de.attacker, Bullet.class);
				Hero hero = em.getComponent(bullet.owner, Hero.class);
				hero.kills++;
			}
		}

		dam.time = Time.getTime();
	}
}
