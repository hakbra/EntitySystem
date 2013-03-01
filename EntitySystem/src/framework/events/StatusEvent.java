package framework.events;

import framework.enums.EventEnum;
import helpers.Time;

public class StatusEvent extends Event{
	public String text;
	public long time;
	
	public StatusEvent(String t)
	{
		this.type = EventEnum.STATUS;
		this.text = t;
		this.time = Time.getTime();
	}
}
