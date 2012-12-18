package framework.systems;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import helpers.Color;
import helpers.Draw;
import helpers.Point;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

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
import framework.components.Item;
import framework.components.Light;
import framework.components.Obstacle;
import framework.components.Particle;
import framework.components.Pathfinder;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Velocity;

public class HudRenderSystem extends CoreSystem {

	public HudRenderSystem(World w)
	{
		super(w);
	}


	@Override
	public void run(EntityManager em)
	{
		for (Entity e : em.getEntityAll(Button.class, Polygon.class))
		{

			glPushMatrix();

			Polygon poly = em.getComponent(e, Polygon.class);
			Button button = em.getComponent(e, Button.class);

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			Tex t = em.getComponent(e, Tex.class);
			
			if (t != null)
				t.render(world, e);
			
			if (!button.active)
				Draw.setColor(Color.WHITE);
			else
				Draw.setColor(new Color(0.5, 0.5, 0.5));
				
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			Draw.write(world.getDataManager().font, poly.mid, button.type);
			GL11.glDisable(GL11.GL_TEXTURE_2D);

			glPopMatrix();
		}


		int i = 0;
		for (Entity e : em.getEntityAll(Hero.class, Health.class))
		{
			Health health = em.getComponent(e, Health.class);

			Integer d = (int) (health.current / health.max * 100);

			Draw.setColor(Color.WHITE);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			Draw.write(world.getDataManager().font, new Point(GLEngine.WIDTH - 75, GLEngine.HEIGHT - 40 - 50*i), "Health: " + d.toString());
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			i++;
		}
	}
}