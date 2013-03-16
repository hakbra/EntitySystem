package zombies.components;

import framework.CoreComponent;
import framework.utils.Point;

public class Velocity extends CoreComponent{
	public Point dir;
	public double speed;
	
	public Velocity(Point d, double s)
	{
		this.dir = d;
		this.speed = s * 2;
		this.name = "Velocity";
	}
}