package framework.systems.render;

import helpers.Draw;
import helpers.Point;
import engine.GLEngine;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Position;
import framework.components.Zombie;
import framework.managers.EntityManager;

public class StatsSystem extends CoreSystem {

	public StatsSystem(World w)
	{
		super(w);
	}


	@Override
	public void run(EntityManager em)
	{
		int i = 0;
		for (CoreEntity hero : em.getEntity(Hero.class))
		{
			Point pos = em.getComponent(hero, Position.class).position;
			Health health = em.getComponent(hero, Health.class);
			Hero h = em.getComponent(hero, Hero.class);

			int p = (int) (100 * health.current / health.max);
			Draw.write(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 40 -60*i), hero.name);
			Draw.write(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 65 -60*i), "Health: " + p + "%");
			Draw.write(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 90 -60*i), "Position: " + pos.intString());
			Draw.write(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 115 -60*i), "Kills: " + h.kills);
			Draw.write(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 140 -60*i), "Zombies: " + em.getEntity(Zombie.class).size());
			i++;
		}
	}
}