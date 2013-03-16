package zombies.components;

import framework.CoreComponent;

public class Angle extends CoreComponent{
	public Double angle;
	
	public Angle(double a)
	{
		this.angle = a;
		this.name = "Angle";
	}
}
