package framework;

import framework.managers.EntityManager;

public class CoreComponent {
	
	protected String name;
	public World world;
	public CoreEntity parent;
	
	public String toString()
	{
		return name;
	}

}
