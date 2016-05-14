package framework.components;

import framework.CoreComponent;

public class Button extends CoreComponent{
	public String type;
	public boolean active;
	
	public Button(String s)
	{
		this.type = s;
		this.name = "StateButton";
		this.active = false;
	}
}
