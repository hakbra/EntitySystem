package zombies.events;

import zombies.utils.Time;
import framework.DynEnum;
import framework.events.Event;

public class StatusEvent extends Event{
	public String text;
	public long time;
	
	public StatusEvent(String t)
	{
		this.type = DynEnum.at("event").get("kill");
		this.text = t;
		this.time = Time.getTime();
	}
}
