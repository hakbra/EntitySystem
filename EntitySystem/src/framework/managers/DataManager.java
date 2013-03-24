package framework.managers;

import java.awt.Font;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import framework.World;
import framework.utils.OpenGLFont;


public class DataManager {
	World world;
	
    public HashMap<String, Integer> textures = new HashMap<>();
    public HashMap<Integer, OpenGLFont> fonts = new HashMap<>();
	
	public DataManager(World w)
	{
		this.world = w;
	}
	public OpenGLFont getFont(int size) {
		OpenGLFont f = fonts.get(size);
		if (f != null)
			return f;
		try
		{
			Font awtFont = new Font("Times New Roman", Font.BOLD, size);
			f = new OpenGLFont(awtFont, false);
			fonts.put(size, f);
			return f;
		}
		catch (Exception e)
		{
			System.out.println("Couldn't load " + size);
			System.exit(0);
		}
		return null;
	}
	
	public void setTexture(String name, int value) {
		textures.put(name, value);
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
}
