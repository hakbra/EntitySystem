package zombies.events;

import framework.enums.EventEnum;
import framework.events.Event;
import framework.utils.Time;

public class StatusEvent extends Event{
	public String text;
	public long time;
	
	public StatusEvent(String t)
	{
		this.type = EventEnum.KILL;
		this.text = t;
		this.time = Time.getTime();
	}
}
