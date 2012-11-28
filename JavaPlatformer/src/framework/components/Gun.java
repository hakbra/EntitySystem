package framework.components;

import framework.Component;
import helpers.Time;

public class Gun extends Component{
	
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
		this.delay = de * 1000*1000;
		this.bullets = b;
		this.name = "Gun";
	}
	
	public boolean canFire()
	{
		if (Time.getTime() - delay > lastFired)
			return true;
		return false;
	}

}
