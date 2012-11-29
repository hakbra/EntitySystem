package framework.components;

import helpers.Color;
import helpers.Point;

public class CircleShape extends Shape {
	
	float radius;

	public CircleShape(Color c, float r)
	{
		super(c);
		this.radius = r;
	}
	
	@Override
	public boolean isInside(Point pos, Point p) {
		if (pos.dist(p) < this.radius)
			return true;
		
		return false;
	}

	@Override
	public Point getClosest(Point pos, Point p) {
		Point dir = pos.sub(p).norm();
		return pos.add(dir.mult(this.radius));
	}

}
