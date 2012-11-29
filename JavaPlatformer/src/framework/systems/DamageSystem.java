package framework.systems;

import helpers.Point;
import helpers.Time;
import framework.Component;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Circle;
import framework.components.Damage;
import framework.components.Health;
import framework.components.Position;
import framework.components.Zombie;




public class DamageSystem extends CoreSystem{

	public DamageSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		long now = Time.getTime();
		
		for (Entity e : em.get(Damage.class))
		{
			Point thisPos = em.getComponent(e, Position.class).position;
			Circle thisCirc = em.getComponent(e, Circle.class);
			Damage thisDam = em.getComponent(e, Damage.class);
			float thisRad = thisCirc.radius;
			
			if (!thisDam.canDamage())
					continue;

			for (Entity e2: em.get(Health.class))
			{
				if (e == e2)
					continue;
				
				if (thisDam.parent == e2)
						continue;
					
				Point otherPos = em.getComponent(e2, Position.class).position;
				Circle otherCirc = em.getComponent(e2, Circle.class);
				Health otherHealth = em.getComponent(e2, Health.class);
				float otherRad = otherCirc.radius;
				
				if (em.hasComponent(e, Zombie.class) && em.hasComponent(e2, Zombie.class))
					continue;
				
				if (thisPos.dist(otherPos) < (thisRad + otherRad) * 1.1)
				{
					otherHealth.current -= thisDam.amount;
					if (otherHealth.current < 0)
						em.removeLater(e2);

					thisDam.time = now;
					
					if (thisDam.timeDelta == 0)
						em.removeLater(e);
				}
			}
		}
	}
}
