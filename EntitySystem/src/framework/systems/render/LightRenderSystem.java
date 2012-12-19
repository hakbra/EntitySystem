package framework.systems.render;

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

public class LightRenderSystem  extends CoreSystem{

	public LightRenderSystem(World w) {
		super(w);
	}

	public void run(EntityManager em)
	{
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_ALPHA,GL11.GL_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
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
}
