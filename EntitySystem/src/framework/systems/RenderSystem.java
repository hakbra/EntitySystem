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
import framework.components.Angle;
import framework.components.Button;
import framework.components.Circle;
import framework.components.ColorComp;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Item;
import framework.components.Light;
import framework.components.Obstacle;
import framework.components.Pathfinder;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.TextureComp;
import framework.components.Velocity;

public class RenderSystem extends CoreSystem {

	int colorTextureID;
	int framebufferID;

	public RenderSystem(EntityManager em)
	{
		super(em);

		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
			System.out.println("FBO not supported!!!");
			System.exit(0);
		}
		else {
			framebufferID = glGenFramebuffersEXT();											// create a new framebuffer
			colorTextureID = glGenTextures();												// and a new texture used as a color buffer

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID); 						// switch to the new framebuffer

			// initialize color texture
			glBindTexture(GL_TEXTURE_2D, colorTextureID);									// Bind the colorbuffer texture
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);				// make it linear filterd
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, GLEngine.WIDTH, GLEngine.HEIGHT, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);	// Create the texture data
			glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorTextureID, 0); // attach it to the framebuffer

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);									// Swithch back to normal framebuffer rendering
		}
	}

	@Override
	public void run(EntityManager em)
	{
		Entity world = em.getByStringID("world");
		Point trans = new Point();

		if (world != null)
			trans = em.getComponent(world, Position.class).position.neg();

		renderGround(em);
		
		glPushMatrix();
		Draw.translate(trans);
		renderItems(em);
		//renderPath(em);
		renderCircles(em);

		renderLights(em);

		Draw.translate(trans);
		renderWalls(em);
		glPopMatrix();

		renderHUD(em);
	}

	private void renderTextures(EntityManager em)
	{
		for (Entity e : em.getEntityAll(TextureComp.class))
		{
			TextureComp t = em.getComponent(e, TextureComp.class);
			t.render(em, e);
		}
	}

	private void renderGround(EntityManager em)
	{
		Draw.setColor(new Color(0.3, 0.3, 0.3, 1));
		Draw.quad( new Point(0, 0), new Point(0, GLEngine.HEIGHT), new Point(GLEngine.WIDTH, GLEngine.HEIGHT), new Point(GLEngine.WIDTH, 0));
	}

	private void renderLights(EntityManager em)
	{
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID); 
		GL11.glBlendFunc(GL11.GL_ONE,GL11.GL_ONE);

		glClearColor (0.1f, 0.1f, 0.1f, 0f);
		glClear (GL_COLOR_BUFFER_BIT);			// Clear Screen And Depth Buffer on the fbo to red
		glColorMask(false, false, false, true);

		for (Entity e : em.getEntityAll(Light.class))
		{
			Light l = em.getComponent(e, Light.class);
			l.render(em, e);
		}
		glColorMask(true, true, true, true);

		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);					// switch to rendering on the framebuffer

		glBindTexture(GL_TEXTURE_2D, colorTextureID);
		Draw.setColor(Color.WHITE);
		GL11.glLoadIdentity();
		//*
		GL14.glBlendEquation(GL14.GL_FUNC_ADD);
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_ALPHA,GL11.GL_SRC_ALPHA);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(	0,							0);
		GL11.glVertex2f(	0,							0);
		GL11.glTexCoord2f(	1,							0);
		GL11.glVertex2f(	GLEngine.WIDTH,	0);
		GL11.glTexCoord2f(	1,							1);
		GL11.glVertex2f(	GLEngine.WIDTH,				GLEngine.HEIGHT);
		GL11.glTexCoord2f(	0,							1);
		GL11.glVertex2f(	0,							GLEngine.HEIGHT);
		GL11.glEnd();
		/**/

		glDisable(GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void renderCircles(EntityManager em)
	{
		for (Entity e : em.getEntityAll(Circle.class, Velocity.class))
		{
			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			if (em.hasComponent(e, Angle.class))
				Draw.rotate(em.getComponent(e, Angle.class).angle);

			if (em.hasComponent(e, ColorComp.class))
				Draw.setColor(em.getComponent(e, ColorComp.class).color);

			if (em.hasComponent(e, TextureComp.class))
			{
				TextureComp t = em.getComponent(e, TextureComp.class);
				t.render(em, e);
			}
			else
			{
				Circle circ = em.getComponent(e, Circle.class);
				circ.render(em, e);
			}

			glPopMatrix();
		}
	}

	private void renderItems(EntityManager em)
	{
		for (Entity e : em.getEntityAll(Item.class))
		{
			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			if (em.hasComponent(e, Angle.class))
				Draw.rotate(em.getComponent(e, Angle.class).angle);


			if (em.hasComponent(e, TextureComp.class))
			{
				TextureComp t = em.getComponent(e, TextureComp.class);
				t.render(em, e);
			}

			glPopMatrix();
		}
	}

	private void renderWalls(EntityManager em)
	{
		for (Entity e : em.getEntityAll(Obstacle.class, Polygon.class))
		{

			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);


			if (em.hasComponent(e, TextureComp.class))
			{
				TextureComp t = em.getComponent(e, TextureComp.class);
				t.render(em, e);
			}
			else
			{
				Polygon poly = em.getComponent(e, Polygon.class);
				poly.render(em, e);
			}

			glPopMatrix();
		}
	}

	private void renderHUD(EntityManager em)
	{
		for (Entity e : em.getEntityAll(Button.class, Polygon.class))
		{

			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			if (em.hasComponent(e, TextureComp.class))
			{
				TextureComp t = em.getComponent(e, TextureComp.class);
				t.render(em, e);
			}
			else
			{
				Polygon poly = em.getComponent(e, Polygon.class);
				poly.render(em, e);
			}

			glPopMatrix();
		}


		int i = 0;
		for (Entity e : em.getEntityAll(Hero.class, Health.class))
		{
			Health health = em.getComponent(e, Health.class);

			glPushMatrix();

			Integer d = (int) (health.current / health.max * 100);


			Draw.setColor(Color.WHITE);
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			em.font.drawString(25, 25 + 50*i, "Health: " + d.toString(), 1, 1);

			GL11.glDisable(GL11.GL_TEXTURE_2D);

			glPopMatrix();
			i++;
		}
	}
	private void renderPath(EntityManager em)
	{
		Entity world = em.getByStringID("world");
		if (world != null)
		{
			Pathfinder pf = em.getComponent(world, Pathfinder.class);
			pf.render();
		}
	}
}
