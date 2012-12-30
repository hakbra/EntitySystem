package framework;

import framework.enums.EventEnum;
import framework.managers.EntityManager;


public abstract class CoreSystem {
	protected World world;
	public boolean enabled;
	public EventEnum event;
	
	public  void run(EntityManager em){}
	
	public CoreSystem(World w)
	{
		this.world = w;
		this.enabled = true;
		this.event = EventEnum.NONE;
	}
}
