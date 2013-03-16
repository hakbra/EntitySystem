package zombies.components;

import framework.CoreComponent;
import framework.utils.Point;

public class Acceleration extends CoreComponent{

	public Point acceleration;
	
	public Acceleration(Point a)
	{
		this.acceleration = a;
		this.name = "Acceleration";
	}
}
