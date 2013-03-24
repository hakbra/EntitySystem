package zombies.components;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import zombies.utils.Draw;


import framework.CoreComponent;
import framework.utils.Color;
import framework.utils.Point;

public class RenderPolygon extends CoreComponent{

	public ArrayList<Point> points;
	public Color color;
	public boolean wireframe;
	
	public RenderPolygon(Color c, Point... ps)
	{
		this.color = c;
		this.name = "CollisionPolygon";
		this.wireframe = false;
		

		points = new ArrayList<Point>();
		for (Point p: ps)
			points.add(p);
	}

	public static RenderPolygon centerRectangle(Point dim, Color c)
	{

		Point p = new Point(0, 0);
		Point w = new Point(dim.x/2, 0);
		Point h = new Point(0, dim.y/2);
		return new RenderPolygon(c, p.add(w).add(h), p.sub(w).add(h), p.sub(w).sub(h), p.add(w).sub(h));
	}

	public static RenderPolygon rectangle(Point dim, Color c) {

		Point p = new Point(0, 0);
		Point w = new Point(dim.x, 0);
		Point h = new Point(0, dim.y);
		return new RenderPolygon(c, p.add(w).add(h), p.add(h), p, p.add(w));
	}
	
	public RenderPolygon wireframe()
	{
		this.wireframe = true;
		return this;
	}
	
	public void render()
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Draw.setColor(this.color);
		
		if (!wireframe)
			Draw.polygon(points);
		else
			for (int i = 0, s = points.size(); i < s; i++)
				Draw.thickLine(points.get(i), points.get( (i+1) == s ? 0 : i+1), 10);
			
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
