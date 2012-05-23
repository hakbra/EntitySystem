package abstracts;

import helpers.Point;

import java.util.ArrayList;

public interface Crashable
{
	public ArrayList<Point> getPoints();

	public boolean contains(Point p);

	public void handleCollision(Object obj);
}
