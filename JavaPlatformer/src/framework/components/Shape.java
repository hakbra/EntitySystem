package framework.components;

import helpers.Color;
import helpers.Point;
import framework.Component;

public abstract class Shape extends Component{
	public Color color;
	
	public Shape(Color c)
	{
		this.color = c;
	}
	
	public  abstract boolean isInside(Point pos, Point p);

	public  abstract Point getClosest(Point pos, Point p);
	
	public abstract void render(Point p);
}
