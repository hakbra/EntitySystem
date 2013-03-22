package zombies.events;

import framework.CoreEntity;
import framework.DynEnum;
import framework.events.Event;

public class TriggerEvent extends Event{
	public CoreEntity hero;
	public CoreEntity trigger;
	
	public TriggerEvent(CoreEntity a, CoreEntity b)
	{
		this.hero = a;
		this.trigger = b;
		this.type = DynEnum.at("event").get("trigger");
	}
}
