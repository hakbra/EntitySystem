package framework.components;

import helpers.Draw;
import helpers.Color;
import helpers.Point;

import org.lwjgl.opengl.GL11;

import framework.CoreComponent;
import framework.CoreEntity;
import framework.managers.EntityManager;

public class Circle extends CoreComponent{

	Point position;
	public float radius;
	
	public Circle(float r)
	{
		this.radius = r;
		this.name = "Circle";
		this.position = new Point();
	}
	
	@Override
	public void entityUpdated(EntityManager em, CoreEntity e)
	{
		if (em.hasComponent(e, Position.class))
			position = em.getComponent(e, Position.class).position;
		else
			position = new Point();
	}
	
	public boolean isInside(Point p)
	{
		return position.dist(p) < this.radius;
	}
	
	public Point getClosest(Point p)
	{
		Point dir = p.sub(position).norm();
		return position.add(dir.mult(this.radius));
	}
}
