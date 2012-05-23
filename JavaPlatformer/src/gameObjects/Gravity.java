package gameObjects;

import helpers.Point;

import java.util.ArrayList;

import abstracts.Crashable;
import abstracts.GameObject;

public class Gravity extends GameObject implements Crashable
{

	@Override
	public void handleCollision(Object obj)
	{
		if (obj instanceof Player)
		{
			Player p = (Player) obj;
			p.setVy(p.getVy() - 1);
		}
	}

	@Override
	public ArrayList<Point> getPoints()
	{
		return new ArrayList<Point>();
	}

	@Override
	public boolean contains(Point p)
	{
		return true;
	}
}
