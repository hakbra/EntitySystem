package framework.components;

import framework.Component;
import helpers.State;

public class Button extends Component{
	public State state;
	
	public Button(State s)
	{
		this.state = s;
		this.name = "Button";
	}
}
