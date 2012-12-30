package framework.components;

import framework.CoreComponent;
import helpers.Point;

public class Velocity extends CoreComponent{
	public Point velocity;
	
	public Velocity(Point v)
	{
		this.velocity = v;
		this.name = "Velocity";
	}
}
