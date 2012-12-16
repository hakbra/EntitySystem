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

	double radius;

	public Light(double r)
	{
		this.radius = r;
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
			if (l.dist(pos) < radius)
				cands.add(l);

		ArrayList<Point> points = new ArrayList<Point>();
		for (double i = 0; i <= 360.01; i += 150 / radius)
		{
			Point ray = new Point(i);
			Line seg = new Line(pos, pos.add(ray.mult(radius)));
			double min = radius;

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
		}

		Color c = new Color(1, 1, 1, 1);
		Draw.setColor(c);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		Draw.vertex(pos);
		for (Point p : points)
		{
			double d = pos.dist(p);
			c.alpha = 1 - d/radius;
			Draw.setColor(c);
			Draw.vertex(p);
		}
		GL11.glEnd();
	}
}
