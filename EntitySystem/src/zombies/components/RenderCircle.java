package zombies.components;

import org.lwjgl.opengl.GL11;

import framework.CoreComponent;
import framework.utils.Color;
import framework.utils.Draw;
import framework.utils.Point;

public class RenderCircle extends CoreComponent{

	public float radius;
	public Color color;
	public boolean wireframe;
	
	public RenderCircle(float r, Color c)
	{
		this.radius = r;
		this.color = c;
		this.name = "CollisionCircle";
		this.wireframe = false;
	}
	
	public RenderCircle wireframe()
	{
		this.wireframe = true;
		return this;
	}
	
	public void render()
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Draw.setColor(this.color);
		
		if (!wireframe)
			Draw.circle(radius);
		else
			Draw.ring(radius, 1);
			
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
