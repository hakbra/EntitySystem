package framework.components;

import org.lwjgl.opengl.GL11;

import helpers.MyColor;
import helpers.Draw;
import helpers.Point;
import helpers.Time;
import framework.Component;
import framework.Entity;
import framework.EntityManager;

public class Circle extends Component implements RenderInterface{
	
	public MyColor color;
	public float radius;
	
	public Circle(float r, MyColor c)
	{
		this.color = c;
		this.radius = r;
		this.name = "Circle";
	}
	public boolean isInside(Point pos, Point p)
	{
		return pos.dist(p) < this.radius;
	}
	
	public Point getClosest(Point pos, Point p)
	{
		Point dir = p.sub(pos).norm();
		return pos.add(dir.mult(this.radius));
	}

	public void render(EntityManager em, Entity e) {
		GL11.glPushMatrix();
		Draw.setColor(color);
		Draw.circle(radius);
		GL11.glPopMatrix();
	}
}
