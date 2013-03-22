package zombies.components;

import framework.CoreComponent;
import framework.utils.Color;
import framework.utils.Draw;
import framework.utils.Point;

public class Text extends CoreComponent{
	public String text;
	
	public Text(String t)
	{
		this.text = t;
		this.name = "Text";
	}
	
	/*
	public LayerEnum getLayer()
	{
		return LayerEnum.TEXT;
	}
	*/
	
	public void render()
	{
		Draw.setColor(Color.WHITE);
		Draw.writeMid(world.getDataManager().font, new Point(), text);
	}

}
