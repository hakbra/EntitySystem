package framework.systems.render;

import helpers.Draw;
import helpers.Point;
import helpers.Time;

import java.util.ArrayList;

import engine.GLEngine;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.EventListener;
import framework.World;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Position;
import framework.components.Zombie;
import framework.events.Event;
import framework.events.StatusEvent;
import framework.managers.EntityManager;

public class StatsSystem extends CoreSystem  implements EventListener{
	
	private ArrayList<StatusEvent> events = new ArrayList<StatusEvent>();

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
			Draw.writeMid(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 40 -60*i), hero.name);
			Draw.writeMid(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 65 -60*i), "Health: " + p + "%");
			Draw.writeMid(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 90 -60*i), "Position: " + pos.intString());
			Draw.writeMid(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 115 -60*i), "Kills: " + h.kills);
			Draw.writeMid(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 140 -60*i), "Zombies: " + em.getEntity(Zombie.class).size());
			i++;
		}

		for (int j = events.size()-1; j >= 0; j--)
		{
			StatusEvent ke = events.get(j);
			Draw.write(world.getDataManager().font, new Point(25, 75 + 25*j), ke.text);
			if (Time.getTime() - ke.time > 3000)
				events.remove(j);
		}
	}

	@Override
	public void recieveEvent(Event e) {
		StatusEvent ke = (StatusEvent) e;
		events.add(ke);
	}
}