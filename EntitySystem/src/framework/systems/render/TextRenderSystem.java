package framework.systems.render;

import helpers.Draw;
import helpers.Point;
import helpers.Time;
import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Message;
import framework.components.Position;

public class TextRenderSystem extends CoreSystem {

	public TextRenderSystem(World w)
	{
		super(w);
	}


	@Override
	public void run(EntityManager em)
	{
		for (Entity e: em.getEntity(Message.class))
		{
			long now = Time.getTime();
			if ((now / 300) % 2 == 1)
				continue;

			String t = em.getComponent(e, Message.class).text;
			Point pos = new Point(GLEngine.WIDTH / 2, GLEngine.HEIGHT / 2);
			Draw.write(world.getDataManager().font, pos, t);
		}
	}
}