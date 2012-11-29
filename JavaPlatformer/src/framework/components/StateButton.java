package framework.components;

import framework.Component;
import helpers.State;

public class StateButton extends Component{
	public State state;
	
	public StateButton(State s)
	{
		this.state = s;
		this.name = "StateButton";
	}
}
