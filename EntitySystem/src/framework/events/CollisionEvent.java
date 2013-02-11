package framework.events;

import helpers.Point;
import framework.CoreEntity;
import framework.enums.EventEnum;

public class CollisionEvent extends Event{
	public CoreEntity collider;
	public CoreEntity obstacle;
	public Point pointOfImpact;
	public boolean inside;
	
	public CollisionEvent(CoreEntity a, CoreEntity b, Point poi, boolean i)
	{
		this.collider = a;
		this.obstacle = b;
		this.pointOfImpact = poi;
		this.inside = i;
		this.type = EventEnum.COLLISION;
	}
}
