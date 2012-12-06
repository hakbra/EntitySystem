package framework.components;

import helpers.Color;
import helpers.Draw;
import helpers.Time;
import framework.Component;
import framework.Entity;
import framework.EntityManager;

public class Gun extends Component implements Render{
	
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

	//@Override
	public void render(EntityManager em, Entity e) {
		Draw.setColor(Color.GREEN);
		Draw.circle(10);
	}
}
