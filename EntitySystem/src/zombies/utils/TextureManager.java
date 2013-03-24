package zombies.utils;

import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureManager {

    public static HashMap<String, Integer> textures = new HashMap<>();

	public static int getTexture(String name) {
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
	public static void setTexture(String name, int value) {
		textures.put(name, value);
	}
}
