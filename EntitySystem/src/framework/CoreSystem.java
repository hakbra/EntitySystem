package framework;

import helpers.Data;
import framework.enums.EventEnum;
import framework.managers.EntityManager;


public abstract class CoreSystem implements EventListener{
	protected World world;
	public boolean enabled;
	public EventEnum event;
	
	public  void run(EntityManager em){}
	
	public CoreSystem()
	{
		this.enabled = true;
		this.event = EventEnum.NONE;
	}
	public CoreSystem(World w)
	{
		this.world = w;
		this.enabled = true;
		this.event = EventEnum.NONE;
	}

	@Override
	public void recieveEvent(Data i) {
		
	}
}
