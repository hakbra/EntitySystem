package framework.systems;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import helpers.Color;
import helpers.Draw;
import helpers.Point;

import org.lwjgl.opengl.GL11;

import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Light;
import framework.components.Position;

public class LightSystem  extends CoreSystem{

	public LightSystem(World w) {
		super(w);
	}

	public void run(EntityManager em)
	{
		calculateLights(em);
		drawLights();
		
	}
	
	private void drawLights()
	{
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_ALPHA,GL11.GL_SRC_ALPHA);
		glBindTexture(GL_TEXTURE_2D, world.getDataManager().lightTexID);
		Draw.setColor(Color.WHITE);

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

		glDisable(GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void calculateLights(EntityManager em)
	{
		Entity cam = em.getByStringID("camera");
		Point trans = new Point();

		if (world != null)
			trans = em.getComponent(cam, Position.class).position.neg();

		Draw.translate(trans);

		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, world.getDataManager().lightBufID); 
		glClearColor (0.1f, 0.1f, 0.1f, 0f);
		glClear (GL_COLOR_BUFFER_BIT);

		GL11.glBlendFunc(GL11.GL_ONE,GL11.GL_ONE);

		glColorMask(false, false, false, true);
		for (Entity e : em.getEntityAll(Light.class))
		{
			Light l = em.getComponent(e, Light.class);
			l.render(em, e);
		}
		glColorMask(true, true, true, true);

		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		GL11.glLoadIdentity();
	}
}
