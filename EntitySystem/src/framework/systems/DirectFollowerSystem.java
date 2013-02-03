package framework.systems;

import helpers.Point;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.DirectFollower;
import framework.components.Hero;
import framework.components.Position;
import framework.components.Velocity;
import framework.managers.EntityManager;


public class DirectFollowerSystem extends CoreSystem{

	public DirectFollowerSystem(World w)
	{
		super(w);
	}

	@Override
	public void run(EntityManager em)
	{
		Point target = null;
		for (CoreEntity e : em.getEntityAll(DirectFollower.class))
		{
			Point pos = em.getComponent(e, Position.class).position;
			Point vel = em.getComponent(e, Velocity.class).velocity;

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
