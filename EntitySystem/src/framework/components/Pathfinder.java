package framework.components;

import helpers.Color;
import helpers.Draw;
import helpers.Point;
import framework.Component;

public class Pathfinder extends Component{

	public class Node implements Comparable{
		public boolean blocked = false;
		public Double value = 10000.0;
		public double dist = 100;
		public Point pos;
		public Point i;
		public long visited;
		public long done;
		@Override
		public int compareTo(Object o) {
			Node n = (Node) o;
			return this.value.compareTo(n.value);
		}
		public String toString()
		{
			return (int)i.x + ", " + (int)i.y + ": " + value; 
		}
	}

	public int width;
	public int height;
	public int step;
	public Node[][] map;
	public long update;

	public Pathfinder(int w, int h, int s)
	{
		this.width = w;
		this.height = h;
		this.step = s;

		map = new Node[w / s + 1][h / s + 1 ];

		for (int i = 0; i <= w / s; i++)
			for (int j = 0; j <= h / s; j++)
			{
				Point p = new Point(i*s, j*s);
				map[i][j] = new Node();
				map[i][j].pos = p;
				map[i][j].i = new Point(i, j);
			}
	}

	public void render()
	{
		double max = 100;
		Color c = new Color(1, 0, 0);
		for (int i = 0; i < width / step; i++)
			for (int j = 0; j < height / step; j++)
			{
				if (map[i][j].blocked)
					continue;
				if (map[i][j].done != update)
					continue;
				c.r = map[i][j].value / max;
				c.g = 1 - map[i][j].value / max;
				Draw.setColor(c);
				Draw.point( map[i][j].pos);
			}
	}

	public boolean isLegal(int i, int j)
	{
		if (i < 0 || i >= width / step)
			return false;
		if (j < 0 || j >= height / step)
			return false;

		return true;
	}

	public Point getDir(Point p)
	{
		Point dir = null;
		double min = -1;
		int n = 2;

		int ix = (int) (p.x / step);
		int iy = (int) (p.y / step);

		for (int i = ix-n; i <= ix+n; i++)
		{
			for (int j = iy-n; j <= iy+n; j++)
			{
				if (isLegal(i, j) && map[i][j].done == update && !map[i][j].blocked && (min < 0 || map[i][j].value < min))
				{
					min = map[i][j].value;
					dir = map[i][j].pos.sub(p).norm();
				}
			}
		}
		if (dir == null)
			return new Point();
		return dir;
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
	
	public void mask(Point a, Point b, long time)
	{
		int ax = (int)(a.x / step);
		int ay = (int)(a.y / step);
		int bx = (int)(b.x / step);
		int by = (int)(b.y / step);
		
		if (ax > width / step)
			ax = width / step;
		if (bx > width / step)
			bx = width / step;

		if (ay > height / step)
			ay = height / step;
		if (by > height / step)
			by = height / step;
		
		if (ax < 0)
			ax = 0;
		if (bx < 0)
			bx = 0;

		if (ay < 0)
			ay = 0;
		if (by < 0)
			by = 0;
		
		int n = (int) (20 / step + 0.5f);

		for (int i = ax-n; i <= bx+n; i++)
		{
			for (int j = ay-n; j <= by+n; j++)
			{
				if (isLegal(i, j))
				{
					map[i][j].done = time;
					map[i][j].blocked = true;
				}
			}
		}
	}

}
