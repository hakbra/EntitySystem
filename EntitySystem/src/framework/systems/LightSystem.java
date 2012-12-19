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
import framework.components.Light;
import framework.components.Position;
import framework.components.Timer;

public class LightSystem  extends CoreSystem{

	public LightSystem(World w) {
		super(w);
	}

	public void run(EntityManager em)
	{
		Entity cam = em.getByStringID("camera");
		Point trans = new Point();

		if (cam != null)
			trans = em.getComponent(cam, Position.class).position.neg();


		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, world.getDataManager().lightBufID); 
		glClearColor (0.1f, 0.1f, 0.1f, 0f);
		glClear (GL_COLOR_BUFFER_BIT);

		GL11.glBlendFunc(GL11.GL_ONE,GL11.GL_ONE);
		glColorMask(false, false, false, true);
		
		GL11.glPushMatrix();
		Draw.translate(trans);
		for (Entity e : em.getEntityAll(Light.class))
		{
			Light l = em.getComponent(e, Light.class);
			
			if (em.hasComponent(e, Timer.class))
			{
				Timer  t = em.getComponent(e, Timer.class);
				l.cRad = (1 - t.getPercent()) * l.mRad;
				if (l.cRad <= 10)
					l.cRad = 10;
			}
			
			l.render(em, e);
		}
		GL11.glPopMatrix();
		
		glColorMask(true, true, true, true);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
	}
}
