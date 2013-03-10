package framework.events;

import framework.CoreEntity;
import framework.enums.EventEnum;
import framework.helpers.Point;

public class DamageEvent extends Event{
	public CoreEntity attacker;
	public CoreEntity receiver;
	
	public DamageEvent(CoreEntity a, CoreEntity b)
	{
		this.attacker = a;
		this.receiver = b;
		this.type = EventEnum.DAMAGE;
	}
}
