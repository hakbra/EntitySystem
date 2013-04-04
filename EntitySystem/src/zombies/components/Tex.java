package zombies.components;


import org.lwjgl.opengl.GL11;

import zombies.utils.Draw;
import zombies.utils.TextureManager;
import framework.CoreComponent;
import framework.utils.Color;
import framework.utils.Point;

public class Tex extends RenderComponent{

	public String texture;
	Point dim;
	Point scale;

	public Tex(String name, Point c)
	{
		this.texture = name;
		this.dim = c;
		this.scale = new Point(0, 0);
		this.name = "Texture";
	}
	
	public Tex setScale(Point s)
	{
		this.scale = s;
		return this;
	}

	@Override
	public void renderSub()
	{
		float w = (float) dim.x / 2;
		float h = (float) dim.y / 2;
		
		float u = 1f;
		float v = 1f;
		if (scale.len() != 0)
		{
			u = (float) (w / scale.x) * 2;
			v = (float) (h / scale.y) * 2;
		}

		Draw.setColor(Color.WHITE);
		
		Button button = world.getEntityManager().getComponent(parent, Button.class);
		if (button != null && !button.active)
			Draw.setColor(new Color(1, 1, 1, 0.5));
		else if (button != null && button.active)
			Draw.setColor(new Color(1, 1, 1, 0.75));
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureManager.getTexture(texture));
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(	0,		0);
		GL11.glVertex2f(	(float)-w,		(float)-h);
		
		GL11.glTexCoord2f(	u,		0);
		GL11.glVertex2f(	(float)+w,		(float)-h);
		
		GL11.glTexCoord2f(	u,		v);
		GL11.glVertex2f(	(float)+w,		(float)+h);
		
		GL11.glTexCoord2f(	0,		v);
		GL11.glVertex2f(	(float)-w,		(float)+h);
		GL11.glEnd();
	}
}
