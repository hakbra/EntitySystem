package framework.events;

import framework.CoreEntity;
import framework.enums.EventEnum;

public class TriggerEvent extends Event{
	public CoreEntity hero;
	public CoreEntity trigger;
	
	public TriggerEvent(CoreEntity a, CoreEntity b)
	{
		this.hero = a;
		this.trigger = b;
		this.type = EventEnum.TRIGGER;
	}
}
