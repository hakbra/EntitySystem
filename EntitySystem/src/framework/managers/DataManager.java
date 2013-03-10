package framework.managers;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import java.awt.Font;
import java.util.HashMap;

import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import framework.World;
import framework.engine.GLEngine;
import framework.helpers.MyFont;


public class DataManager {
	World world;
	
	public MyFont font;
    public HashMap<String, Integer> textures = new HashMap<String, Integer>();
	
	public DataManager(World w)
	{
		this.world = w;

		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new MyFont(awtFont, false);
	}

	public int getTexture(String name) {
		Integer t = textures.get(name);
		if (t != null)
			return t;
		try
		{
			Texture tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + name));
			textures.put(name, tex.getTextureID());
			return tex.getTextureID();
		}
		catch (Exception e)
		{
			System.out.println("Couldn't load " + name);
			System.exit(0);
		}
		return -1;
	}

	public void createLightTexture()
	{
		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
			System.out.println("FBO not supported!!!");
			System.exit(0);
		}
		else {
			int lightBufID = glGenFramebuffersEXT();
			int lightTexID = glGenTextures();

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, lightBufID);

			glBindTexture(GL_TEXTURE_2D, lightTexID);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, GLEngine.WIDTH, GLEngine.HEIGHT, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);
			glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, lightTexID, 0);

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
			
			textures.put("lightTex", lightTexID);
			textures.put("lightBuf", lightBufID);
		}
	}
}
