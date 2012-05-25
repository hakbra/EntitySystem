package helpers;

import interfaces.Crashable;
import interfaces.Crasher;
import interfaces.MotionInterface;
import interfaces.RectangleInterface;

import java.util.ArrayList;

import objects.Motion;
import objects.Rectangle;

public class Collision
{
	public static boolean check(Crasher a, Crashable b)
	{
		ArrayList<Point> points = a.getPoints();

		for (Point p : points)
			if (b.contains(p))
				return true;

		return false;
	}

	public static int blockPlayerReaction(RectangleInterface a, RectangleInterface b)
	{
		Rectangle ar = a.getRectangle();
		Rectangle br = b.getRectangle();

		Motion am = new Motion();
		Motion bm = new Motion();

		ArrayList<Point> cPoints = br.getPoints();
		for (int i = 0; i < cPoints.size();)
			if (!ar.contains(cPoints.get(i)))
				cPoints.remove(i);
			else
				i++;

		if (a instanceof MotionInterface)
			am = ((MotionInterface) a).getMotion();
		if (b instanceof MotionInterface)
			bm = ((MotionInterface) b).getMotion();

		int vx = am.getVX() + bm.getVX();
		int vy = am.getVY() + bm.getVY();

		if (cPoints.size() == 0)
			return 0;
		else if (cPoints.size() == 1)
		{
			Point p1 = cPoints.get(0);
			int dx = p1.x - ar.getX();
			int dy = p1.y - ar.getY();

			if (vx == 0)
			{
				if (dy < ar.getH() / 2)
				{
					br.setY(ar.getY() - br.getH());
					return 3;
				} else
				{
					br.setY(ar.getY() + ar.getH());
					bm.setVY(0);
					return 1;
				}
			} else if (vy == 0)
			{
				if (dx < ar.getW() / 2)
				{
					br.setX(ar.getX() - br.getW());
					bm.setVX(0);
					return 4;
				} else
				{
					br.setX(ar.getX() + ar.getW());
					bm.setVX(0);
					return 2;
				}
			} else
			{
				int dxc = dx;
				int dyc = dy;

				if (dx > ar.getW() / 2)
					dxc = ar.getX() + ar.getW() - p1.x;
				if (dy > ar.getH() / 2)
					dyc = ar.getY() + ar.getH() - p1.y;

				if (dxc < dyc)
					if (dx < ar.getW() / 2)
					{
						br.setX(ar.getX() - br.getW());
						bm.setVX(0);
						return 4;
					} else
					{
						br.setX(ar.getX() + ar.getW());
						bm.setVX(0);
						return 2;
					}
				else
				{
					if (dy < ar.getH() / 2)
					{
						br.setY(ar.getY() - br.getH());
						return 3;
					} else
					{
						br.setY(ar.getY() + ar.getH());
						bm.setVY(0);
						return 1;
					}
				}
			}
		} else if (cPoints.size() == 2)
		{
			Point p1 = cPoints.get(0);
			Point p2 = cPoints.get(1);
			int dx1 = p1.x - ar.getX();
			int dy1 = p1.y - ar.getY();
			int dx2 = p2.x - ar.getX();
			int dy2 = p2.y - ar.getY();

			if (dx1 == dx2)
			{
				if (dx1 < ar.getW() / 2)
				{
					br.setX(ar.getX() - br.getW());
					bm.setVX(0);
					return 4;
				} else
				{
					br.setX(ar.getX() + ar.getW());
					bm.setVX(0);
					return 2;
				}
			} else if (dy1 == dy2)
			{
				if (dy1 < ar.getH() / 2)
				{
					br.setY(ar.getY() - br.getH());
					return 3;
				} else
				{
					br.setY(ar.getY() + ar.getH());
					bm.setVY(0);
					return 1;
				}
			}
		}

		return 0;
	}
}
