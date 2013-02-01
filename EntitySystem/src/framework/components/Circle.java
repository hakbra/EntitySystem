package framework.components;

import helpers.Point;
import framework.CoreComponent;

public class Circle extends CoreComponent{

	public float radius;
	
	public Circle(float r)
	{
		this.radius = r;
		this.name = "Circle";
	}
	
	public Point getPosition()
	{
		if (world.getEntityManager().hasComponent(parent, Position.class))
			return world.getEntityManager().getComponent(parent, Position.class).position;
		return null;
	}
	
	public boolean isInside(Point p)
	{
		return getPosition().dist(p) < this.radius;
	}
	
	public Point getClosest(Point p)
	{
		Point pos = getPosition();
		Point dir = p.sub(pos).norm();
		return pos.add(dir.mult(this.radius));
	}
}
