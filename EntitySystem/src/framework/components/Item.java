package framework.components;

import framework.CoreComponent;

public class Item extends CoreComponent{
	
	public String type;
	public float value;
	
	public Item(String t)
	{
		this.type = t;
		this.value = 0;
	}

	public Item(String t, float v)
	{
		this.type = t;
		this.value = v;
	}
}
