package framework.components;

import helpers.Color;
import helpers.Draw;
import helpers.Point;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import framework.Component;
import framework.Entity;
import framework.EntityManager;
import framework.World;

public class Tex extends Component{

	public String texture;
	Point coords;
	Point offset;
	
	public Tex(String name)
	{
		this.texture = name;
		this.coords = new Point(0, 0);
		this.offset = new Point(0, 0);
	}

	public Tex(String name, Point c)
	{
		this.texture = name;
		this.coords = c;
		this.offset = new Point(0, 0);
	}

	public Tex(String name, Point c, Point o)
	{
		this.texture = name;
		this.coords = c;
		this.offset = o;
	}
	
	public void render(World world, Entity e) {
		EntityManager em = world.getEntityManager();
		Draw.setColor(new Color(1, 1, 1));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, world.getDataManager().getTexture(texture).getTextureID());
		
		if (em.hasComponent(e, Circle.class))
		{
			float rad = em.getComponent(e, Circle.class).radius;
			float w = (float) rad;
			float h = (float) rad;
			
			if (coords.x != 0 || coords.y != 0)
			{
				w *= coords.x;
				h *= coords.y;
			}
			
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(	1,		1);
			GL11.glVertex2f(	-w + (float)offset.x,	-h + (float)offset.y);
			
			GL11.glTexCoord2f(	1,		0);
			GL11.glVertex2f(	w + (float)offset.x,	-h + (float)offset.y);
			
			GL11.glTexCoord2f(	0,		0);
			GL11.glVertex2f(	w + (float)offset.x,	h + (float)offset.y);
			
			GL11.glTexCoord2f(	0,		1);
			GL11.glVertex2f(	-w + (float)offset.x,	h + (float)offset.y);
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
			//Draw.setColor(Color.RED);
			//Draw.ring(rad, 2);
		}
		else if (em.hasComponent(e, Polygon.class))
		{
			if (em.hasComponent(e, Button.class))
				Draw.setColor(new Color(1, 1, 1, 0.5));
			
			Polygon poly = em.getComponent(e, Polygon.class);
			GL11.glBegin(GL11.GL_POLYGON);
			ArrayList<Point> points = poly.localPoints;
			for (Point p : points)
			{
				if (coords.x == 0 && coords.y == 0)
				{
					GL11.glTexCoord2f(
							(float) ((p.x - poly.min.x) / (poly.max.x - poly.min.x)),
							(float) ((p.y - poly.min.y) / (poly.max.y - poly.min.y)));
				}
				else
				{
					GL11.glTexCoord2f(
							(float) ((p.x - poly.min.x) / coords.x),
							(float) ((p.y - poly.min.y) / coords.y) );
				}
				GL11.glVertex2f  ((float) p.x,(float)  p.y);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
		

		
	}
}
