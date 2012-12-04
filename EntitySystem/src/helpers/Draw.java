package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Draw {

	static double PI = 3.141592;
	
	public static void vertex(Point p)
	{
		glVertex3f((float)p.x, (float)p.y, 0);
	}
	
	public static void setColor(Color d)
	{
		glColor3f(d.a*d.brightness, d.b*d.brightness, d.c*d.brightness);
	}
	
	public static void point(Point p)
	{
		glBegin(GL_POINTS);
		glVertex3f((float) p.x, (float) p.y, 0);
		glEnd();
	}
	
	public static void triangle(Point a, Point b, Point c)
	{
		glBegin(GL_TRIANGLES);
		vertex(a);
		vertex(b);
		vertex(c);
		glEnd();
	}
	
	public static void quad(Point a, Point b, Point c, Point d)
	{
		glBegin(GL_QUADS);
		vertex(a);
		vertex(b);
		vertex(c);
		vertex(d);
		glEnd();
	}
	
	public static void line(Point a, Point b)
	{
		glBegin(GL_LINES);
		vertex(a);
		vertex(b);
		glEnd();
	}

	public static void circle(float r, Point p)
	{
		glBegin(GL_TRIANGLE_FAN);
		vertex(p);
		for (float i = 0; i <= 2*PI+0.1; i += 0.1)
		{
			Point a = new Point(Math.cos(i)*r, Math.sin(i)*r);
			vertex(p.add(a));
		}
		glEnd();
	}

	public static void polygon(Point pos, ArrayList<Point> points) {
		glBegin(GL_POLYGON);
		for (Point p : points)
			vertex(pos.add(p));
		glEnd();
	}
}
