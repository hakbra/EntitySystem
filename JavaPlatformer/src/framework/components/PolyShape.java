package framework.components;

import java.util.ArrayList;

import helpers.Color;
import helpers.Draw;
import helpers.Point;

public class PolyShape extends Shape {

	public ArrayList<Point> points;

	public PolyShape(Color c, Point... ps)
	{
		super(c);
		
		points = new ArrayList<Point>();
		for (Point p: ps)
			points.add(p);
	}
	
	public static Shape rectangle(Color c, Point dim)
	{

		Point p = new Point(0, 0);
		Point w = new Point(dim.x, 0);
		Point h = new Point(0, dim.y);
		
		return new PolyShape(c, p, p.add(h), p.add(h).add(w), p.add(w));
	}
	
	@Override
	public boolean isInside(Point pos, Point p)
	{
		Point source = p;
		Point ray = p.add(Point.Y);
		
		int sum = 0;
		for (int i = 0; i < points.size(); i++)
		{
			Point p1 = pos.add(points.get(i));
			Point p2 = pos.add(points.get((i+1)%points.size()));
			
			Point col = Point.rayToSegment(source, ray, p1, p2);
			
			if (col != null)
				sum += 1;
		}
		return sum % 2 != 0;
	}

	@Override
	public Point getClosest(Point pos, Point p) {
		Point closest = pos;
		double dist = pos.dist(p);
		
		for (int i = 0; i < points.size(); i++)
		{
			Point p1 = pos.add(points.get(i));
			Point p2 = pos.add(points.get((i+1)%points.size()));
			
			Point tempClosest = p.pointOnLine(p1, p2);
			double tempDist = tempClosest.dist(p);
			
			if (tempDist < dist)
			{
				closest = tempClosest;
				dist = tempDist;
			}
		}
		
		return closest;
	}

	@Override
	public void render(Point pos) {
		Draw.polygon(pos, this.points);
		
	}
}
