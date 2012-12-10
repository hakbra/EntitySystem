package framework.components;

import helpers.Draw;
import helpers.MyColor;
import helpers.Point;
import helpers.Time;

import org.lwjgl.opengl.GL11;

import framework.Component;
import framework.Entity;
import framework.EntityManager;

public class Gun extends Component implements RenderInterface{
	
	public float damage;
	public float spread;
	public float delay;
	public float speed;
	public int bullets;
	
	public long lastFired;
	
	public Gun(float da, float s, float sp, float de, int b)
	{
		this.damage = da;
		this.spread = s;
		this.speed = sp;
		this.delay = de;
		this.bullets = b;
		this.name = "Gun";
	}
	
	public boolean canFire()
	{
		if (Time.getTime() - delay > lastFired)
			return true;
		return false;
	}

	public void render(EntityManager em, Entity e)
	{
		float r = em.getComponent(e, Circle.class).radius;
		GL11.glPushMatrix();
		Draw.setColor(new MyColor(1, 1, 1, 1));
		Draw.translate(-0.5f);
		Draw.quad(new Point(0, 5), new Point(r+5, 5), new Point(r+5, -5), new Point(0, -5));
		GL11.glPopMatrix();
	}
}
