package framework.components;

import helpers.Draw;
import helpers.MyColor;
import helpers.Point;
import helpers.MyFont;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import framework.Component;
import framework.Entity;
import framework.EntityManager;

public class Polygon extends Component implements RenderInterface{

	public ArrayList<Point> points;
	public MyColor color;
	public Point mid;

	public Polygon(MyColor c, Point... ps)
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

	public static Polygon rectangle(MyColor c, Point dim)
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
			String text = em.getComponent(e, Button.class).type;
			double w = em.font.getWidth(text) - 8*text.length();
			double h = em.font.getHeight();
			
			Draw.setColor(MyColor.WHITE);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			em.font.drawString((float) (mid.x - w/2), (float) (mid.y - h / 2), text, 1, 1);
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
		
		GL11.glPopMatrix();
		
	}
}
