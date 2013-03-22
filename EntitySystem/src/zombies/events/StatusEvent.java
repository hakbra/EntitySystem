package zombies.events;

import framework.DynEnum;
import framework.events.Event;
import framework.utils.Time;

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
