package framework;

import java.util.ArrayList;


public abstract class CoreSystem {
	protected World world;
	public boolean enabled;
	
	public  void run(EntityManager em){}
	
	public CoreSystem(World w)
	{
		this.world = w;
		this.enabled = true;
	}
}
