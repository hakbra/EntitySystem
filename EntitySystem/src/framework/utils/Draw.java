package framework.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class Draw {

	static double PI = 3.141592;
	
	public static void vertex(Point p)
	{
		glVertex3f((float)p.x, (float)p.y, 0);
	}
	
	public static void setColor(Color c)
	{
		glColor4f((float) (c.r), (float) (c.g), (float) (c.b), (float) c.alpha);
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

	public static void ring(float r, float t)
	{
		ring(r, t, 180);
	}

	public static void ring(float r, float t, int g)
	{
		glBegin(GL_TRIANGLE_STRIP);
		for (int i = -g; i <= g; i += 1)
		{
			double rad = i*PI/180;
			Point a = new Point(Math.cos(rad)*r, Math.sin(rad)*r);
			Point b = new Point(Math.cos(rad)*(r-t), Math.sin(rad)*(r-t));
			vertex(a);
			vertex(b);
		}
		glEnd();
	}

	public static void polygon(ArrayList<Point> points) {
		glBegin(GL_POLYGON);
		for (Point p : points)
			vertex(p);
		glEnd();
	}
	
	public static void rotate(double a)
	{
		glRotatef((float) a, 0f, 0f, 1f);
	}

	public static void translate(Point p)
	{
		glTranslatef((float) p.x, (float) p.y, 0f);
	}
	
	public static void circle(float r)
	{
		glBegin(GL_POLYGON);
		for (float i = 0; i <= 2*PI+0.1; i += 0.1)
		{
			Point a = new Point(Math.cos(i)*r, Math.sin(i)*r);
			vertex(a);
		}
		glEnd();
	}
	
	public static void writeMid(OpenGLFont f, Point p, String t)
	{

		double w = f.getWidth(t) - 8*t.length();
		double h = f.getHeight();
		
		f.drawString((float) (p.x - w / 2), (float) (p.y - h / 2), t, 1, 1);
	}

	
	public static void write(OpenGLFont f, Point p, String t)
	{
		double h = f.getHeight();
		
		f.drawString((float) (p.x), (float) (p.y - h / 2), t, 1, 1);
	}

	public static void thickLine(Point point, Point point2, double t) {
		Point w = point2.sub(point).norm(t).rot(-90);
		Draw.quad(point, point2, point2.add(w), point.add(w));
	}
}
