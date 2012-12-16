package framework;

import java.awt.Font;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import helpers.MyFont;

public class DataManager {
	World world;

	public MyFont font;
    private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public DataManager(World w)
	{
		this.world = w;

		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new MyFont(awtFont, false);
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
}
