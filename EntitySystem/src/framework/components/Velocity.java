package framework.components;

import framework.Component;
import helpers.Point;

public class Velocity extends Component{
	public Point velocity;
	
	public Velocity(Point v)
	{
		this.velocity = v;
		this.name = "Velocity";
	}
}
