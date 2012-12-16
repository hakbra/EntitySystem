package framework;

import java.util.ArrayList;


public abstract class CoreSystem {
	protected World world;
	
	public  void run(EntityManager em){}
	
	public CoreSystem(World w)
	{
		this.world = w;
	}
}
