package framework.components;

import helpers.Color;
import framework.Component;

public class Circle extends Component{
	
	public Color color;
	public float radius;
	
	public Circle(float r, Color c)
	{
		this.color = c;
		this.radius = r;
		this.name = "Circle";
	}

}
