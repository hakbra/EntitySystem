package framework.components;

import org.lwjgl.opengl.GL11;

import helpers.Color;
import helpers.Draw;
import helpers.Point;
import framework.CoreComponent;

public class RenderCircle extends CoreComponent{

	public float radius;
	public Color color;
	
	public RenderCircle(float r, Color c)
	{
		this.radius = r;
		this.color = c;
		this.name = "CollisionCircle";
	}
	
	public void render()
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Draw.setColor(this.color);
		Draw.circle(radius);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
