package framework.components;

import helpers.Color;
import helpers.Point;

import java.util.ArrayList;

import framework.Component;

public class Polygon extends Component{
	
	public ArrayList<Point> points;
	public Color color;
	
	public Polygon(Color c, Point... ps)
	{
		this.color = c;

		points = new ArrayList<Point>();
		for (Point p: ps)
			points.add(p);
	}
	
	public static Polygon rectangle(Color c, Point dim)
	{

		Point p = new Point(0, 0);
		Point w = new Point(dim.x, 0);
		Point h = new Point(0, dim.y);
		
		return new Polygon(c, p, p.add(h), p.add(h).add(w), p.add(w));
	}
	
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
}
