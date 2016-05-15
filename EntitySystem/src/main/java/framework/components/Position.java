package framework.components;

import helpers.Point;
import framework.CoreComponent;

public class Position extends CoreComponent {
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
	
	public Position setFixed()
	{
		local = true;
		return this;
	}

	@Override
	public String toString() {
		return "[" + position.x + ", " + position.y + "]";
	}
}
