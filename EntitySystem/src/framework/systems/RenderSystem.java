package framework.systems;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import helpers.Draw;
import helpers.MyColor;
import helpers.Point;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Light;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.TextureComp;

public class RenderSystem extends CoreSystem {

	public RenderSystem(EntityManager em)
	{
		super(em);
	}

	@Override
	public void run(EntityManager em)
	{

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE ,GL11.GL_ONE);
		GL14.glBlendEquation(GL14.GL_FUNC_ADD);
		GL11.glColorMask(false, false, false, true);
		for (Entity e : em.getEntityAll(Light.class))
		{
			Light l = em.getComponent(e, Light.class);
			l.render(em, e);
		}
		
		GL11.glColorMask(true, true, true, true);
		GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE);
		
		Draw.setColor(new MyColor(0.3, 0.3, 0.3, 1));
		Draw.quad( new Point(0, 0), new Point(0, GLEngine.HEIGHT), new Point(GLEngine.WIDTH, GLEngine.HEIGHT), new Point(GLEngine.WIDTH, 0));
		
		for (Entity e : em.getEntityAll(Circle.class))
		{
			Circle circ = em.getComponent(e, Circle.class);

			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			if (em.hasComponent(e, Angle.class))
				Draw.rotate(em.getComponent(e, Angle.class).angle);

			if (em.hasComponent(e, Gun.class))
				em.getComponent(e, Gun.class).render(em, e);

			circ.render(em, e);

			//if (em.hasComponent(e, Health.class))
			//	em.getComponent(e, Health.class).render(em, e);

			glPopMatrix();
		}
		GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for (Entity e : em.getEntityAll(TextureComp.class))
		{
			TextureComp t = em.getComponent(e, TextureComp.class);

			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			if (em.hasComponent(e, Angle.class))
				Draw.rotate(em.getComponent(e, Angle.class).angle);

			t.render(em, e);

			glPopMatrix();
		}

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		for (Entity e : em.getEntityAll(Obstacle.class, Polygon.class))
		{
			Polygon poly = em.getComponent(e, Polygon.class);

			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			poly.render(em, e);

			glPopMatrix();
		}

		for (Entity e : em.getEntityAll(Button.class, Polygon.class))
		{
			Polygon poly = em.getComponent(e, Polygon.class);

			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			poly.render(em, e);

			glPopMatrix();
		}

		
		int i = 0;
		for (Entity e : em.getEntityAll(Hero.class, Health.class))
		{
			Health health = em.getComponent(e, Health.class);

			glPushMatrix();

			Integer d = (int) (health.current / health.max * 100);


			Draw.setColor(MyColor.WHITE);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			em.font.drawString(25, 25 + 50*i, "Health: " + d.toString(), 1, 1);
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);

			glPopMatrix();
			i++;
		}
	}
}
