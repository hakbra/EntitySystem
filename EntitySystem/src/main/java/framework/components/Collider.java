package framework.components;

import framework.CoreComponent;

public class Collider extends CoreComponent{
	
	public int level;
	
	public Collider(int i)
	{
		this.level = i;
		this.name = "Collider";
	}

}
