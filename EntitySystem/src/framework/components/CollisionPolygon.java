package framework.components;

import helpers.Line;
import helpers.Point;

import java.util.ArrayList;

import framework.CoreComponent;

public class CollisionPolygon extends CoreComponent{

	public ArrayList<Point> localPoints;
	public Point mid;
	public Point min, max;
	public boolean inverted;

	public CollisionPolygon(Point... ps)
	{
		mid = new Point();

		localPoints = new ArrayList<Point>();
		min = new Point(ps[0]);
		max = new Point(ps[0]);
		for (Point p: ps)
		{
			localPoints.add(p);
			mid.iadd(p);
			
			if (p.x < this.min.x)
				this.min.x = p.x;
			if (p.x > this.max.x)
				this.max.x = p.x;
			if (p.y < min.y)
				min.y = p.y;
			if (p.y > max.y)
				max.y = p.y;
		}
		mid.idiv(localPoints.size());
		
		inverted = false;
		name = "CollisionPolygon";
	}

	public static CollisionPolygon centerRectangle(Point dim)
	{

		Point p = new Point(0, 0);
		Point w = new Point(dim.x/2, 0);
		Point h = new Point(0, dim.y/2);
		return new CollisionPolygon(p.add(w).add(h), p.sub(w).add(h), p.sub(w).sub(h), p.add(w).sub(h));
	}

	public static CollisionPolygon invertedRectangle(Point dim)
	{

		Point p = new Point(0, 0);
		Point w = new Point(dim.x, 0);
		Point h = new Point(0, dim.y);
		
		CollisionPolygon poly = new CollisionPolygon(p, p.add(h), p.add(h).add(w), p.add(w));
		poly.inverted = true;

		return poly; 
	}
	
	public Point getPosition()
	{
		if (world.getEntityManager().hasComponent(parent, Position.class))
			return world.getEntityManager().getComponent(parent, Position.class).position;
		return new Point();
	}
	
	public double getAngle()
	{
		if (world.getEntityManager().hasComponent(parent, Angle.class))
			return world.getEntityManager().getComponent(parent, Angle.class).angle;
		return 0;
	}
	
	public double getScale()
	{
		if (world.getEntityManager().hasComponent(parent, Scale.class))
			return world.getEntityManager().getComponent(parent, Scale.class).scale;
		return 1;
	}

	public boolean isInside(Point p)
	{
		Line ray = new Line(p, p.add(Point.Y));

		int sum = 0;
		for (Line l : getLines())
		{
			Point col = l.intersectionRay(ray);

			if (col != null)
				sum += 1;
		}
		boolean inside = sum % 2 != 0;
		
		if (inverted)
			inside = !inside;
		
		return inside;
	}
	
	public Point getClosest(Point p)
	{
		Point closest = null;
		double dist = -1;

		for (Line l : getLines())
		{
			Point tempClosest = l.pointOnLine(p);
			double tempDist = tempClosest.dist(p);

			if (dist < 0 || tempDist < dist)
			{
				closest = tempClosest;
				dist = tempDist;
			}
		}

		return closest;
	}
	
	public ArrayList<Point> getPoints()
	{
		ArrayList<Point> worldPoints = new ArrayList<Point>();
		
		for (Point p : localPoints)
		{
			Point t = p.rot(getAngle());
			t = t.mult(getScale());
			t = t.add(getPosition());
			worldPoints.add(t);
		}
		
		return worldPoints;
	}
	
	public ArrayList<Line> getLines()
	{
		ArrayList<Point> worldPoints = getPoints();
		ArrayList<Line> worldLines = new ArrayList<Line>();
		

		for (int i = 0; i < worldPoints.size(); i++)
		{
			Point p1 = worldPoints.get(i);
			Point p2 = worldPoints.get((i+1)%worldPoints.size());

			worldLines.add( new Line(p1, p2));
		}
		
		return worldLines;
	}
}
