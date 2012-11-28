package framework.components;

import framework.Component;

public class Circle extends Component{
	
	public String color;
	public float radius;
	
	public Circle(float r, String c)
	{
		this.color = c;
		this.radius = r;
		this.name = "Circle";
	}

}
