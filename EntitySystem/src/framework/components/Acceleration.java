package framework.components;

import helpers.Point;
import framework.Component;

public class Acceleration extends Component{

	public Point acceleration;
	
	public Acceleration(Point a)
	{
		this.acceleration = a;
		this.name = "Acceleration";
	}
}
