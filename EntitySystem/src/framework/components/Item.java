package framework.components;

import framework.Component;

public class Item extends Component{
	
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
