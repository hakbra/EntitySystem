package helpers;

import gameObjects.Block;
import gameObjects.Player;

import java.util.ArrayList;

import abstracts.Crashable;

public class Collision
{
	public static boolean check(Crashable a, Crashable b)
	{
		ArrayList<Point> points = a.getPoints();

		for (Point p : points)
			if (b.contains(p))
				return true;

		points = b.getPoints();

		for (Point p : points)
			if (a.contains(p))
				return true;

		return false;
	}

	public static int blockPlayerReaction(Block b, Player p)
	{
		ArrayList<Point> cPoints = p.getPoints();
		for (int i = 0; i < cPoints.size();)
			if (!b.contains(cPoints.get(i)))
				cPoints.remove(i);
			else
				i++;

		if (cPoints.size() == 0)
			return 0;
		else if (cPoints.size() == 1)
		{
			Point p1 = cPoints.get(0);
			int dx = p1.x - b.getX();
			int dy = p1.y - b.getY();

			if (p.getVx() == 0)
			{
				p.setVy(0);
				if (dy < b.getH() / 2)
				{
					p.setY(b.getY() - p.getH());
					return 3;
				} else
				{
					p.setY(b.getY() + b.getH());
					return 1;
				}
			} else if (p.getVy() == 0)
			{
				if (dx < b.getW() / 2)
				{
					p.setX(b.getX() - p.getW());
					return 4;
				} else
				{
					p.setX(b.getX() + b.getW());
					return 2;
				}
			} else
			{
				int dxc = dx;
				int dyc = dy;

				if (dx > b.getW() / 2)
					dxc = b.getX() + b.getW() - p1.x;
				if (dy > b.getH() / 2)
				{
					dyc = b.getY() + b.getH() - p1.y;
				}

				if (dxc < dyc)
					if (dx < b.getW() / 2)
					{
						p.setX(b.getX() - p.getW());
						return 4;
					} else
					{
						p.setX(b.getX() + b.getW());
						return 2;
					}
				else
				{
					if (dy < b.getH() / 2)
					{
						p.setY(b.getY() - p.getH());
						return 3;
					} else
					{
						p.setY(b.getY() + b.getH());
						return 1;
					}
				}
			}
		} else if (cPoints.size() == 2)
		{
			Point p1 = cPoints.get(0);
			Point p2 = cPoints.get(1);
			int dx1 = p1.x - b.getX();
			int dy1 = p1.y - b.getY();
			int dx2 = p2.x - b.getX();
			int dy2 = p2.y - b.getY();

			if (dx1 == dx2)
			{
				if (dx1 < b.getW() / 2)
				{
					p.setX(b.getX() - p.getW());
					return 4;
				} else
				{
					p.setX(b.getX() + b.getW());
					return 2;
				}
			} else if (dy1 == dy2)
			{
				p.setVy(0);
				if (dy1 < b.getH() / 2)
				{
					p.setY(b.getY() - p.getH());
					return 3;
				} else
				{
					p.setY(b.getY() + b.getH());
					return 1;
				}
			}
		}

		return 0;
	}
}
