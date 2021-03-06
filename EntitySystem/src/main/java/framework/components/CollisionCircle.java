package framework.components;

import helpers.Point;
import framework.CoreComponent;

public class CollisionCircle extends CoreComponent{

	public float radius;
	
	public CollisionCircle(float r)
	{
		this.radius = r;
		this.name = "CollisionCircle";
	}
	
	public Point getPosition()
	{
		if (world.getEntityManager().hasComponent(parent, Position.class))
			return world.getEntityManager().getComponent(parent, Position.class).position;
		return new Point();
	}
	
	public double getScale()
	{
		if (world.getEntityManager().hasComponent(parent, Scale.class))
			return world.getEntityManager().getComponent(parent, Scale.class).scale;
		return 1;
	}
	
	public double getRadius()
	{
		return radius * getScale();
	}
	
	public boolean isInside(Point p)
	{
		return getPosition().dist(p) < this.radius*getScale();
	}
	
	public Point getClosest(Point p)
	{
		Point pos = getPosition();
		Point dir = p.sub(pos).norm();
		return pos.add(dir.mult(this.radius*getScale()));
	}
}
