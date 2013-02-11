package framework.managers;

import java.util.ArrayList;
import java.util.HashMap;

import framework.CoreSystem;
import framework.EventListener;
import framework.World;
import framework.enums.EventEnum;
import framework.events.Event;

public class EventManager {
	HashMap<EventEnum, ArrayList<EventListener>> listenerLists;
	World world;
	
	public EventManager(World w)
	{
		this.world = w;
		listenerLists = new HashMap<EventEnum, ArrayList<EventListener>>();
	}
	
	private ArrayList<EventListener> getListeners(EventEnum e)
	{
		ArrayList<EventListener> listeners = listenerLists.get(e);
		if (listeners == null)
		{
			listeners = new ArrayList<EventListener>();
			listenerLists.put(e, listeners);
		}
		return listeners;
	}
	
	public void addListener(EventEnum ee, EventListener el)
	{
		ArrayList<EventListener> listeners = getListeners(ee);
		listeners.add(el);
	}
	
	public void sendEvent(Event e)
	{
		ArrayList<EventListener> listeners = getListeners(e.type);
		for(EventListener el : listeners)
			el.recieveEvent(e);
	}
}
