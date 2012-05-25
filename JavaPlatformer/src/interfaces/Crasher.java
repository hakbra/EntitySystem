package interfaces;

import helpers.Point;

import java.util.ArrayList;

public interface Crasher
{
	public ArrayList<Point> getPoints();

	public void handleCollision(Object obj);
}
