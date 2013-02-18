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

import framework.CoreSystem;
import framework.CoreEntity;
import framework.World;
import framework.components.Light;
import framework.components.Position;
import framework.components.Timer;
import framework.managers.EntityManager;

public class LightSystem  extends CoreSystem{

	public LightSystem(World w) {
		super(w);
	}

	public void run(EntityManager em)
	{
		CoreEntity cam = em.getByStringID("camera");
		Point trans = new Point();

		if (cam != null)
			trans = em.getComponent(cam, Position.class).position.neg();

		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, world.getDataManager().getLightBufID()); 
		glClearColor (0.1f, 0.1f, 0.1f, 1f);
		glClear (GL_COLOR_BUFFER_BIT);

		GL14.glBlendEquation(GL14.GL_MIN);
		glColorMask(false, false, false, true);
		
		GL11.glPushMatrix();
		Draw.translate(trans);
		for (CoreEntity e : em.getEntityAll(Light.class))
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
		GL14.glBlendEquation(GL14.GL_FUNC_ADD);
	}
	
	@Override
	public void stop()
	{
		super.stop();
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, world.getDataManager().getLightBufID()); 
		glClearColor (1f, 1f, 1f, 0f);
		glClear (GL_COLOR_BUFFER_BIT);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}
}
