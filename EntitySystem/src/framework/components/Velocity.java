package framework.components;

import framework.CoreComponent;
import helpers.Point;

public class Velocity extends CoreComponent{
	public Point dir;
	public double speed;
	
	public Velocity(Point d, double s)
	{
		this.dir = d;
		this.speed = s;
		this.name = "Velocity";
	}
}