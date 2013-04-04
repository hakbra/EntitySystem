package zombies.components;

import framework.CoreComponent;
import framework.utils.Point;

public abstract class CollisionComponent extends CoreComponent {

	
	public abstract Point getPosition();
	
	public abstract double getScale();
	
	public abstract boolean isInside(Point p);
	
	public abstract Point getClosest(Point p);

	public abstract Point getMin();
	
	public abstract Point getMax();
}
