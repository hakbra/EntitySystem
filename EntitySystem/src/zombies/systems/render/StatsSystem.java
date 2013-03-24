package zombies.systems.render;


import java.util.ArrayList;

import zombies.components.Health;
import zombies.components.Hero;
import zombies.components.Position;
import zombies.components.Zombie;
import zombies.events.StatusEvent;
import zombies.utils.Draw;
import zombies.utils.FontManager;
import zombies.utils.OpenGLFont;
import zombies.utils.Time;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.DynEnum;
import framework.engine.GLEngine;
import framework.events.Event;
import framework.interfaces.EventListener;
import framework.managers.EntityManager;
import framework.managers.EventManager;
import framework.utils.Point;

public class StatsSystem extends CoreSystem  implements EventListener{
	
	private ArrayList<StatusEvent> events;
	
	@Override
	public void init ()
	{
		EventManager em = world.getEventManager();
		em.addListener(DynEnum.at("event").get("kill"), this);
		
		events = new ArrayList<StatusEvent>();
	}

	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		OpenGLFont font = FontManager.getFont(24);
		
		int i = 0;
		for (CoreEntity hero : em.getEntity(Hero.class))
		{
			Point pos = em.getComponent(hero, Position.class).position;
			Health health = em.getComponent(hero, Health.class);
			Hero h = em.getComponent(hero, Hero.class);

			int p = (int) (100 * health.current / health.max);
			Draw.writeMid(font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 40 -60*i), hero.name);
			Draw.writeMid(font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 65 -60*i), "Health: " + p + "%");
			Draw.writeMid(font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 90 -60*i), "Position: " + pos.intString());
			Draw.writeMid(font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 115 -60*i), "Kills: " + h.kills);
			Draw.writeMid(font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 140 -60*i), "Zombies: " + em.getEntity(Zombie.class).size());
			i++;
		}

		for (int j = events.size()-1; j >= 0; j--)
		{
			StatusEvent ke = events.get(j);
			Draw.write(font, new Point(25, 75 + 25*j), ke.text);
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