package zombies.components;

import framework.CoreComponent;
import framework.CoreEntity;
import framework.utils.Time;



public class Damage extends CoreComponent{
	
	public float amount;
	public long time;
	public int timeDelta;
	public CoreEntity parent;
	
	public Damage(float a, int t)
	{
		this.parent = null;
		this.amount = a;
		this.time = -1;
		this.timeDelta = t;
		this.name = "Damage";
	}

	public Damage(float a, CoreEntity e)
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
