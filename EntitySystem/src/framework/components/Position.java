package framework.components;

import framework.Component;
import helpers.Point;

public class Position extends Component {
	public Point position;
	public boolean local;
	
	public Position(Point p)
	{
		this.position = p;
		this.local = false;
		this.name = "Position";
	}
	public Position(Point p, boolean l)
	{
		this.position = p;
		this.local = l;
		this.name = "Position";
	}
}
