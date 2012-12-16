package framework;

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

import engine.GLEngine;

import helpers.MyFont;

public class DataManager {
	World world;

	public MyFont font;
    public HashMap<String, Texture> textures = new HashMap<String, Texture>();
    
    public int lightTexID;
    public int lightBufID;
	
	public DataManager(World w)
	{
		this.world = w;

		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new MyFont(awtFont, false);
		createTexture();
	}

	public Texture getTexture(String name) {
		Texture t = textures.get(name);
		if (t != null)
			return t;
		
		try
		{
			t = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + name));
		}
		catch (Exception e)
		{
			System.out.println("Couldn't load " + name);
			System.exit(0);
		}
		textures.put(name, t);
		return t;
	}

	private void createTexture()
	{
		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
			System.out.println("FBO not supported!!!");
			System.exit(0);
		}
		else {
			lightBufID = glGenFramebuffersEXT();
			lightTexID = glGenTextures();

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, lightBufID);

			glBindTexture(GL_TEXTURE_2D, lightTexID);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, GLEngine.WIDTH, GLEngine.HEIGHT, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);
			glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, lightTexID, 0);

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		}
	}
}
