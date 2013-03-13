package framework.components;

import framework.CoreComponent;
import framework.utils.Point;

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
}