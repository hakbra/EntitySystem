package framework;

import framework.enums.EventEnum;
import framework.enums.StateEnum;
import framework.events.Event;
import framework.managers.EntityManager;


public abstract class CoreSystem implements EventListener{
	protected World world;
	public boolean enabled;
	public StateEnum state;
	
	public  void run(EntityManager em){}
	
	public CoreSystem()
	{
		this.enabled = true;
	}
	public CoreSystem(World w)
	{
		this.world = w;
		this.enabled = true;
	}
	
	public void subscribe(EventEnum type)
	{
		world.getEventManager(state).addListener(type, this);
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

	@Override
	public void recieveEvent(Event e) {
		
	}
}
