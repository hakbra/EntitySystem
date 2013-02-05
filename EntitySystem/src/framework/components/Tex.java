package framework.components;

import helpers.Color;
import helpers.Draw;
import helpers.Point;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import framework.CoreComponent;
import framework.CoreEntity;
import framework.World;
import framework.enums.LayerEnum;
import framework.managers.EntityManager;

public class Tex extends CoreComponent{

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
	public void render()
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
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, world.getDataManager().getTexture(texture));
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(	0,		0);
		GL11.glVertex2f(	-w,		-h);
		
		GL11.glTexCoord2f(	u,		0);
		GL11.glVertex2f(	w,		-h);
		
		GL11.glTexCoord2f(	u,		v);
		GL11.glVertex2f(	w,		h);
		
		GL11.glTexCoord2f(	0,		v);
		GL11.glVertex2f(	-w,		h);
		GL11.glEnd();
	}
}
