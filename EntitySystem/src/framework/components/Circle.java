package framework.components;

import helpers.Draw;
import helpers.Color;
import helpers.Point;

import org.lwjgl.opengl.GL11;

import framework.Component;
import framework.Entity;
import framework.EntityManager;

public class Circle extends Component{
	
	public Color color;
	public float radius;
	
	public Circle(float r, Color c)
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
