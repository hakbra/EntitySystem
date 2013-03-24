package zombies.utils;

import java.awt.Font;
import java.util.HashMap;

public class FontManager {

    public static HashMap<Integer, OpenGLFont> fonts = new HashMap<>();
	
	public static OpenGLFont getFont(int size) {
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
}
