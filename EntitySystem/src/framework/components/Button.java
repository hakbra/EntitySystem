package framework.components;

import framework.Component;

public class Button extends Component{
	public String type;
	
	public Button(String s)
	{
		this.type = s;
		this.name = "StateButton";
	}
}
