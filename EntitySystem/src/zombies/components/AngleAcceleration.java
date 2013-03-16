package zombies.components;

import framework.CoreComponent;

public class AngleAcceleration extends CoreComponent{

	public double acc;
	
	public AngleAcceleration(double s)
	{
		this.acc = s;
		this.name = "AngleAcceleration";
	}
}
