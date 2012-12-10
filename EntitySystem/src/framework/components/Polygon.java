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
	public Color color;
	public Point mid;

	public Polygon(Color c, Point... ps)
	{
		this.color = c;
		mid = new Point();

		points = new ArrayList<Point>();
		for (Point p: ps)
		{
			points.add(p);
			mid.iadd(p);
		}
		mid.idiv(points.size());
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
		Draw.setColor(color);
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
