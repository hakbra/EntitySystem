package framework;

import interfaces.EventListener;
import framework.enums.EventEnum;
import framework.enums.StateEnum;
import framework.events.Event;
import framework.managers.EntityManager;


public abstract class CoreSystem{
	protected World world;
	public boolean enabled;
	public StateEnum state;
	
	public  void run(EntityManager em){}
	
	public CoreSystem()
	{
		this.enabled = true;
	}
	
	public void subscribe(EventEnum type)
	{
		if (this instanceof EventListener)
			world.getEventManager().addListener(type, (EventListener) this);
	}
	
	public void stop()
	{
		enabled = false;
	}
	
	public void start()
	{
		enabled = true;
	}
	
	public void toggle()
	{
		if (enabled)
			stop();
		else
			start();
	}
}
