package zombies.systems;

import zombies.components.Angle;
import zombies.components.AngleSpeed;
import zombies.components.DirectFollower;
import zombies.components.Hero;
import zombies.components.Position;
import zombies.components.Velocity;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.managers.EntityManager;
import framework.utils.Point;


public class DirectFollowerSystem extends CoreSystem{


	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		Point target = null;
		for (CoreEntity e : em.getEntityAll(DirectFollower.class))
		{
			Point pos = em.getComponent(e, Position.class).position;
			Point vel = em.getComponent(e, Velocity.class).dir;

			for (CoreEntity hero : em.getEntityAll(Hero.class))
			{
				Point heroPos = em.getComponent(hero, Position.class).position;

				if (target == null || pos.dist(target) > pos.dist(heroPos))
					target = heroPos;
			}

			if (target == null)
				continue;

			vel.set(target.sub(pos).norm(1));

			if (em.hasComponents(e, Angle.class))
			{
				Angle a = em.getComponent(e, Angle.class);
				a.angle = vel.angle();
			}
		}
	}
}
