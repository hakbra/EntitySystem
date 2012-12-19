package framework.components;

import helpers.Color;
import helpers.Draw;
import helpers.Point;
import engine.GLEngine;
import framework.Component;

public class Pathfinder extends Component{

	public class Node implements Comparable{
		public boolean blocked = false;
		public Double value = 10000.0;
		public Point pos;
		public Point i;
		public long visited;
		public long done;
		public Node prev;
		@Override
		public int compareTo(Object o) {
			Node n = (Node) o;
			return this.value.compareTo(n.value);
		}
		public String toString()
		{
			return (int)i.x + ", " + (int)i.y + ": " + value + ", blocked: " + blocked; 
		}
	}

	public int width;
	public int height;
	public int step;
	public Node[][] map;
	public long update;

	public Pathfinder(Point min, Point max, int s)
	{
		this.step = s;
		this.width = (int) (max.x - min.x) / s;
		this.height = (int) (max.y - min.y) / s;

		map = new Node[width][height];

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
			{
				Point p = new Point(i*s, j*s);
				map[i][j] = new Node();
				map[i][j].pos = p;
				map[i][j].i = new Point(i, j);
			}
	}

	public void render()
	{
		Color c = new Color(1, 0, 0);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
			{
				if (map[i][j].blocked)
					continue;
				if (map[i][j].done != update)
					continue;
				
					int max = 80;
					double w = map[i][j].value;
					c.r = w / max;
					c.g = 1 - w / max;
					
				Draw.setColor(c);
				Draw.point(map[i][j].pos);
			}
	}

	public boolean isLegal(int i, int j)
	{
		if (i < 0 || i >= width)
			return false;
		if (j < 0 || j >= height)
			return false;

		return true;
	}

	public Point getDir(Point p, double limit)
	{
		double min = -1;
		Node node = null;
		int n = 2;

		int ix = (int) (p.x / step);
		int iy = (int) (p.y / step);

		for (int i = ix-n; i <= ix+n; i++)
		{
			for (int j = iy-n; j <= iy+n; j++)
			{
				if (!isLegal(i, j))
					continue;
				if (map[i][j].done == update && !map[i][j].blocked && (min < 0|| map[i][j].value < min))
				{
					min = map[i][j].value;
					node = map[i][j];
				}
			}
		}

		if (node == null)
			return new Point();
		if (limit > 0 && node.value > limit)
			return new Point();

		return node.pos.sub(p).norm();
	}

	public Node getNode(Point p)
	{
		int ix = (int) (p.x / step);
		int iy = (int) (p.y / step);

		if (isLegal(ix, iy))
			return map[ix][iy];
		else
			return null;

	}

	public void mask(Polygon poly, Point pos, long time)
	{
		Point a = poly.min.add(pos);
		Point b = poly.max.add(pos);

		int ax = (int)(a.x / step);
		int ay = (int)(a.y / step);
		int bx = (int)(b.x / step);
		int by = (int)(b.y / step);

		if (ax >= width)
			ax = width-1;
		if (bx >= width)
			bx = width-1;

		if (ay >= height)
			ay = height - 1;
		if (by >= height)
			by = height - 1;

		if (ax < 0)
			ax = 0;
		if (bx < 0)
			bx = 0;

		if (ay < 0)
			ay = 0;
		if (by < 0)
			by = 0;

		int n = (int) (20 / step);

		for (int i = ax-n; i <= bx+n; i++)
		{
			for (int j = ay-n; j <= by+n; j++)
			{
				if (isLegal(i, j))
				{
					if (poly.isInside(map[i][j].pos) || map[i][j].pos.dist(poly.getClosest(map[i][j].pos)) < 20)
					{
						map[i][j].done = time;
						map[i][j].blocked = true;
					}
				}
			}
		}
	}

}
