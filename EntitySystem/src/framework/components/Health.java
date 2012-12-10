package framework.components;

import helpers.MyColor;
import helpers.Draw;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import framework.Component;
import framework.Entity;
import framework.EntityManager;



public class Health extends Component implements RenderInterface{
	
	public float current;
	public float max;
	
	public Health()
	{
		this.current = 100;
		this.max = 100;
		this.name = "Health";
	}

	public void render(EntityManager em, Entity e) {
		float r = em.getComponent(e, Circle.class).radius;
		float d = 1 - current / max;

		GL11.glPushMatrix();
		
		Draw.setColor(MyColor.GREEN);
		Draw.ring(r, 5);

		Draw.setColor(MyColor.RED);
		Draw.ring(r, 5, (int) (180 * d));
		GL11.glPopMatrix();
	}
}
