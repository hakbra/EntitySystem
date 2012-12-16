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

public class TextureComp extends Component{

	String texture;
	
	public TextureComp(String name)
	{
		this.texture = name;
	}
	
	public void render(World w, Entity e) {
		EntityManager em = w.getEntityManager();
		Draw.setColor(new Color(1, 1, 1, 1));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, w.getDataManager().getTexture(texture).getTextureID());
		
		if (em.hasComponent(e, Circle.class))
		{
			GL11.glBegin(GL11.GL_QUADS);
			float rad = em.getComponent(e, Circle.class).radius;
			GL11.glTexCoord2f(	1,		1);
			GL11.glVertex2f(	-rad,	-rad);
			
			GL11.glTexCoord2f(	1,		0);
			GL11.glVertex2f(	rad,	-rad);
			
			GL11.glTexCoord2f(	0,		0);
			GL11.glVertex2f(	rad,	rad);
			
			GL11.glTexCoord2f(	0,		1);
			GL11.glVertex2f(	-rad,	rad);
			GL11.glEnd();
		}
		else if (em.hasComponents(e, Polygon.class, Button.class))
		{
			Polygon poly = em.getComponent(e, Polygon.class);
			ArrayList<Point> points = poly.localPoints;
			String text = em.getComponent(e, Button.class).type;

			Draw.setColor(new Color(1, 1, 1, 0.5));

			GL11.glBegin(GL11.GL_POLYGON);
			for (Point p : points)
			{
				GL11.glTexCoord2f((float) (p.x / (poly.mid.x*2)),(float) 	(p.y / (poly.mid.y*2)));
				GL11.glVertex2f  ((float) p.x,(float)  p.y);
			}
			GL11.glEnd();

			Draw.setColor(new Color(1, 1, 1, 1));
			Draw.write(w.getDataManager().font, poly.mid, text);
		}
		else if (em.hasComponent(e, Polygon.class))
		{
			Polygon poly = em.getComponent(e, Polygon.class);
			GL11.glBegin(GL11.GL_POLYGON);
			ArrayList<Point> points = poly.localPoints;
			for (Point p : points)
			{
				GL11.glTexCoord2f((float) p.x / 50,(float) 	p.y / 50);
				GL11.glVertex2f  ((float) p.x,(float)  p.y);
			}
			GL11.glEnd();
		}
		

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
	}
}
