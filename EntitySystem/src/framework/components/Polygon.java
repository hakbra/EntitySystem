package framework.components;

import helpers.Draw;
import helpers.Color;
import helpers.Point;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import framework.Component;
import framework.Entity;
import framework.EntityManager;

public class Polygon extends Component{

	public ArrayList<Point> points;
	public Point mid;
	public Point min, max;
	public boolean inverted;

	public Polygon(Point... ps)
	{
		mid = new Point();

		points = new ArrayList<Point>();
		min = new Point(ps[0]);
		max = new Point(ps[0]);
		for (Point p: ps)
		{
			points.add(p);
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
		mid.idiv(points.size());
		
		inverted = false;
	}

	public static Polygon rectangle(Point dim)
	{

		Point p = new Point(0, 0);
		Point w = new Point(dim.x, 0);
		Point h = new Point(0, dim.y);

		return new Polygon(p, p.add(h), p.add(h).add(w), p.add(w));
	}

	public static Polygon invertedRectangle(Point dim)
	{

		Point p = new Point(0, 0);
		Point w = new Point(dim.x, 0);
		Point h = new Point(0, dim.y);
		
		Polygon poly = new Polygon(p, p.add(h), p.add(h).add(w), p.add(w));
		poly.inverted = true;

		return poly; 
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
		boolean inside = sum % 2 != 0;
		if (inverted)
			inside = !inside;
		
		return inside;
	}
	
	public Point getClosest(Point pos, Point p)
	{
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

	public void render(EntityManager em, Entity e) {
		GL11.glPushMatrix();
		Draw.translate(0.5f);
		Draw.polygon(points);
		
		if (em.hasComponent(e, Button.class))
		{
			Draw.setColor(Color.WHITE);
			String text = em.getComponent(e, Button.class).type;
			Draw.write(em.font, mid, text);
		}
		
		GL11.glPopMatrix();
		
	}
}
