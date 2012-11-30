package framework;

import java.util.ArrayList;


public abstract class CoreSystem {
	EntityManager em;
	
	public  void run(EntityManager em){}
	
	public CoreSystem(EntityManager em)
	{
		this.em = em;
	}
}
