package framework.components;

import helpers.Time;
import framework.Component;
import framework.Entity;



public class Damage extends Component{
	
	public float amount;
	public long time;
	public int timeDelta;
	public Entity parent;
	
	public Damage(float a, int t)
	{
		this.parent = null;
		this.amount = a;
		this.time = -1;
		this.timeDelta = t * 1000000;
		this.name = "Damage";
	}

	public Damage(float a, Entity e)
	{
		this.parent = e;
		this.amount = a;
		this.timeDelta = 0;
		this.name = "Damage";
	}
	
	public boolean canDamage()
	{
		if (Time.getTime() > time + timeDelta)
			return true;
		return false;
	}
}
