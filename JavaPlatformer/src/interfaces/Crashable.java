package interfaces;

import helpers.Point;

public interface Crashable
{
	public boolean contains(Point p);

	public void handleCollision(Object obj);
}
