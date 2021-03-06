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
import helpers.Draw;
import helpers.Point;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import engine.GLEngine;
import framework.CoreSystem;
import framework.CoreEntity;
import framework.World;
import framework.components.Light;
import framework.components.Position;
import framework.components.Timer;
import framework.managers.EntityManager;

public class LightSystem  extends CoreSystem{

	@Override
	public void init()
	{
		world.getDataManager().createLightTexture();
	}

	public void run()
	{
		EntityManager em = world.getEntityManager();

		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, world.getDataManager().getTexture("lightBuf")); 
		glClearColor (0.1f, 0.1f, 0.1f, 1f);
		glClear (GL_COLOR_BUFFER_BIT);

		GL14.glBlendEquation(GL14.GL_MIN);
		glColorMask(false, false, false, true);
		
		double xratio = (double) GLEngine.WIDTH / world.mapdim.x;
		double yratio = (double) GLEngine.HEIGHT / world.mapdim.y;
		
		GL11.glPushMatrix();
		GL11.glScaled(xratio, yratio, 1);
		for (CoreEntity e : em.getEntityAll(Light.class))
		{
			Light l = em.getComponent(e, Light.class);
			l.render(em, e);
		}
		GL11.glPopMatrix();
		
		glColorMask(true, true, true, true);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		GL14.glBlendEquation(GL14.GL_FUNC_ADD);
	}
	
	@Override
	public void disable()
	{
		super.disable();
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, world.getDataManager().getTexture("lightBuf")); 
		glClearColor (0.1f, 0.1f, 0.1f, 0f);
		glClear (GL_COLOR_BUFFER_BIT);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}
}
