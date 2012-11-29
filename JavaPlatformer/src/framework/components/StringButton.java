package framework.components;

import framework.Component;

public class StringButton extends Component{
	public String type;
	
	public StringButton(String s)
	{
		this.type = s;
		this.name = "StateButton";
	}
}
