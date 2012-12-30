package framework.components;

import helpers.Point;
import framework.CoreComponent;

public class Acceleration extends CoreComponent{

	public Point acceleration;
	
	public Acceleration(Point a)
	{
		this.acceleration = a;
		this.name = "Acceleration";
	}
}
