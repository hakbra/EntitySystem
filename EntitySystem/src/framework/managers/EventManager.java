package framework.managers;

import helpers.Intersection;

import java.util.HashMap;

import framework.EventListener;
import framework.World;
import framework.enums.EventEnum;

public class EventManager {
	HashMap<EventEnum, EventListener> listeners;
	World world;
	
	public EventManager(World w)
	{
		this.world = w;
		listeners = new HashMap<EventEnum, EventListener>();
	}
	
	public void addListener(EventEnum ee, EventListener el)
	{
		listeners.put(ee, el);
	}
	
	public void sendEvent(EventEnum ee, Intersection i)
	{
		EventListener el = listeners.get(ee);
		if (el != null)
			el.action(i);
	}
}
