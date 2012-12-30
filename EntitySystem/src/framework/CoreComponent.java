package framework;

import framework.managers.EntityManager;

public class CoreComponent {
	
	protected String name;
	
	public void entityUpdated(EntityManager em, CoreEntity e)
	{
		
	}
	
	public String toString()
	{
		return name;
	}

}
