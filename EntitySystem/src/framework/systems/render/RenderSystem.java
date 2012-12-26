package framework.systems.render;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import helpers.Color;
import helpers.Draw;
import helpers.Point;
import helpers.Time;

import org.lwjgl.opengl.GL11;

import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Angle;
import framework.components.Button;
import framework.components.Circle;
import framework.components.ColorComp;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Message;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;

public class RenderSystem extends CoreSystem {

	public RenderSystem(World w)
	{
		super(w);
	}


	@Override
	public void run(EntityManager em)
	{
		Entity cam = em.getByStringID("camera");
		Point camPos = new Point();
		if (cam != null)
			camPos = em.getComponent(cam, Position.class).position.neg();
		
		for (Entity e: em.renders)
		{
			glPushMatrix();

			if (em.hasComponent(e, Position.class))
			{
				Position pos = em.getComponent(e, Position.class);
				if (!pos.local)
					Draw.translate(camPos);
				Draw.translate(pos.position);
			}

			if (em.hasComponent(e, Angle.class))
				Draw.rotate(em.getComponent(e, Angle.class).angle);
			
			if (em.hasComponent(e, ColorComp.class))
				Draw.setColor(em.getComponent(e, ColorComp.class).color);

			if (em.hasComponent(e, Tex.class))
			{
				Draw.setColor(Color.WHITE);
				em.getComponent(e, Tex.class).render(world, e);
			}
			else if (em.hasComponent(e, Circle.class))
			{
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				Circle circle = em.getComponent(e, Circle.class);
				Draw.circle(circle.radius);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}

			if (em.hasComponent(e, Button.class))
			{
				Button b = em.getComponent(e, Button.class);
				Polygon poly = em.getComponent(e, Polygon.class);
				if (!b.active)
					Draw.setColor(Color.WHITE);
				else
					Draw.setColor(new Color(0.6, 0.6, 0.6));
				Draw.write(world.getDataManager().font, poly.mid, b.type);
			}

			glPopMatrix();
		}

		Draw.setColor(Color.WHITE);
		int i = 0;
		for (Entity h : em.getEntity(Hero.class))
		{
			Health health = em.getComponent(h, Health.class);
			int p = (int) (100 * health.current / health.max);
			Draw.write(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 40 -60*i), h.name);
			Draw.write(world.getDataManager().font, new Point(GLEngine.WIDTH - 100, GLEngine.HEIGHT - 65 -60*i), "Health: " + p + "%");
			i++;
		}
	}
}