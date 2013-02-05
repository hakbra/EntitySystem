package framework.components;

import helpers.Draw;
import helpers.Point;
import framework.CoreComponent;
import framework.enums.LayerEnum;

public class Text extends CoreComponent{
	public String text;
	
	public Text(String t)
	{
		this.text = t;
		this.name = "Text";
	}
	
	public LayerEnum getLayer()
	{
		return LayerEnum.TEXT;
	}
	
	public void render()
	{
		Draw.write(world.getDataManager().font, new Point(), text);
	}

}
