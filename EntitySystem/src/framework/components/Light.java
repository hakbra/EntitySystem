package framework.components;

import helpers.Color;
import helpers.Draw;
import helpers.Line;
import helpers.Point;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import framework.Component;
import framework.Entity;
import framework.EntityManager;

public class Light extends Component{

	public double mRad;
	public double cRad;

	public Light(double r)
	{
		this.mRad = r;
		this.cRad = r;
	}

	public void render(EntityManager em, Entity e) {
		ArrayList<Line> edges = new ArrayList<Line>();
		Point pos = em.getComponent(e, Position.class).position;

		for (Entity p : em.getEntityAll(Polygon.class, Obstacle.class))
		{
			Polygon poly = em.getComponent(p, Polygon.class);
			edges.addAll(poly.getLines());
		}

		ArrayList<Line> cands = new ArrayList<Line>();
		for (Line l : edges)
			if (l.dist(pos) < cRad)
				cands.add(l);

		ArrayList<Point> points = new ArrayList<Point>();
		double step = 150 / cRad;
		for (double i = 0;;)
		{
			Point ray = new Point(i);
			Line seg = new Line(pos, pos.add(ray.mult(cRad)));
			double min = cRad;

			for (Line l : cands)
			{
				Point col = l.intersectionLine(seg);
				if (col != null)
				{
					double tempMin = pos.dist(col);
					if (tempMin < min)
						min = tempMin;
				}
			}
			points.add( pos.add(ray.mult(min)));
			
			if (i + step <= 360)
				i += step;
			else if (i == 360)
				break;
			else
				i = 360;
		}

		Color c = new Color(1, 1, 1, 0);
		Draw.setColor(c);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		Draw.vertex(pos);
		for (Point p : points)
		{
			double d = pos.dist(p);
			c.alpha = d/cRad;
			Draw.setColor(c);
			Draw.vertex(p);
		}
		GL11.glEnd();
	}
}
