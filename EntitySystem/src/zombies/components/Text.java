package zombies.components;

import zombies.utils.Draw;
import zombies.utils.FontManager;
import framework.CoreComponent;
import framework.utils.Color;
import framework.utils.Point;

public class Text extends RenderComponent{
	public String text;
	
	public Text(String t)
	{
		this.text = t;
		this.name = "Text";
	}
	
	public void render()
	{
		Draw.setColor(Color.WHITE);
		Draw.writeMid(FontManager.getFont(24), new Point(), text);
	}

}
