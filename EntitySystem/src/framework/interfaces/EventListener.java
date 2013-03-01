package framework.interfaces;

import framework.events.Event;
import helpers.Data;

public interface EventListener<Type> {
	public void recieveEvent(Type event);
}
