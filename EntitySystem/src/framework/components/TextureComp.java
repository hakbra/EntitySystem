package framework.components;

import helpers.Draw;
import helpers.MyColor;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import framework.Component;
import framework.Entity;
import framework.EntityManager;

public class TextureComp extends Component{

	Texture texture;
	
	public TextureComp(String name)
	{
		try
		{
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + name));
		}
		catch (Exception e)
		{
			System.out.println("Couldn't load " + name);
			System.exit(0);
		}
	}
	
	public void render(EntityManager em, Entity e) {
		Draw.setColor(MyColor.WHITE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(	0,							0);
		GL11.glVertex2f(	0,							0);
		GL11.glTexCoord2f(	1,							0);
		GL11.glVertex2f(	texture.getTextureWidth(),	0);
		GL11.glTexCoord2f(	1,							1);
		GL11.glVertex2f(	texture.getTextureWidth(),	texture.getTextureHeight());
		GL11.glTexCoord2f(	0,							1);
		GL11.glVertex2f(	0,							texture.getTextureHeight());
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
}
