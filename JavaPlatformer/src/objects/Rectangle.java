package objects;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import helpers.Point;
import interfaces.Crashable;

import java.util.ArrayList;

public class Rectangle implements Crashable
{
	protected int x, y;
	protected int w, h;

	public Rectangle(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void render(int dx, int dy)
	{
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x + w, y);
		glVertex2f(x + w, y + h);
		glVertex2f(x, y + h);
		glEnd();
	}

	public ArrayList<Point> getPoints()
	{
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(x, y));
		points.add(new Point(x + w, y));
		points.add(new Point(x, y + h));
		points.add(new Point(x + w, y + h));
		return points;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void addX(int d)
	{
		x += d;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void addY(int d)
	{
		y += d;
	}

	public int getW()
	{
		return w;
	}

	public void setW(int w)
	{
		this.w = w;
	}

	public int getH()
	{
		return h;
	}

	public void setH(int h)
	{
		this.h = h;
	}

	public boolean contains(Point p)
	{
		if (p.x <= x || p.x >= x + w)
			return false;
		if (p.y <= y || p.y >= y + h)
			return false;

		return true;
	}

	public void move(Motion m)
	{
		x += m.getVX();
		y += m.getVY();
	}

	@Override
	public void handleCollision(Object obj)
	{
	}
}
