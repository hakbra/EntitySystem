package framework.events;

import framework.enums.EventEnum;
import helpers.Time;

public class KillEvent extends Event{
	public String text;
	public long time;
	
	public KillEvent(String t)
	{
		this.type = EventEnum.KILL;
		this.text = t;
		this.time = Time.getTime();
	}
}
