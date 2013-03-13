package framework.utils;

public class Point
{
	public static Point X = new Point(1, 0);
	public static Point Y = new Point(0, 1);
	public static Point NULL = new Point(0, 1);
	public static double PI = 3.141592;
	public double x, y;

	public Point()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public Point(Point p)
	{
		this.x = p.x;
		this.y = p.y;
	}
	
	public Point(double a)
	{
		double d = a * PI/180;
		this.x = Math.cos(d);
		this.y = Math.sin(d);
	}
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public void set(Point p)
	{
		this.x = p.x;
		this.y = p.y;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public Point add(Point p)
	{
		Point n = new Point();
		
		n.x = this.x + p.x;
		n.y = this.y + p.y;
		
		return n;
	}
	
	public double dist(Point p)
	{
		Point diff = this.sub(p);
		return diff.len();
	}
	
	public void iadd(Point p)
	{
		this.x += p.x;
		this.y += p.y;
	} 

	public void isub(Point p)
	{
		this.x -= p.x;
		this.y -= p.y;
	}
	
	public Point sub(Point p)
	{
		Point n = new Point();
		
		n.x = this.x - p.x;
		n.y = this.y - p.y;
		
		return n;
	}

	public Point div(double p)
	{
		Point n = new Point();
		
		n.x = this.x / p;
		n.y = this.y / p;
		
		return n;
	}

	public Point mult(double p)
	{
		Point n = new Point();
		
		n.x = this.x * p;
		n.y = this.y * p;
		
		return n;
	}

	public Point rot(double a)
	{
		double r = a * PI/180;
		
		float cs = (float) Math.cos(r);
		float sn = (float) Math.sin(r);
		
		Point n = new Point();
		
		n.x = this.x * cs - this.y * sn; 
		n.y = this.x * sn + this.y * cs;
		
		return n;
	}
	
	public double angle()
	{
		return Math.atan2(this.y, this.x) * 180/PI;
	}
	public double angle(Point other)
	{
	      return Math.atan2(other.cross(this), other.dot(this)) * 180/PI;
	}

	public double mag()
	{
		return this.x*this.x + this.y*this.y;
	}
	
	public double len()
	{
		return Math.sqrt(this.x*this.x + this.y*this.y);
	}
	
	public Point norm()
	{
		Point n = new Point();
		double l = this.len();
		if (l != 0)
			n = this.div(this.len());
		return n;
	}
	public Point norm(double p)
	{
		Point n = new Point();
		double l = this.len();
		if (l != 0)
			n = this.div(this.len()).mult(p);
		return n;
	}
	
	public Point pointOnLine(Point a, Point b)
	{
		Point ac = this.sub(a);
		Point ab = b.sub(a);
		
		double r = ac.dot(ab) / ab.mag();
		
		if (r < 0)
			r = 0;
		if (r > 1)
			r = 1;
		
		return a.add(ab.mult(r));
	}
	
	public int sideOfLine(Point a, Point b)
	{
		Point ac = this.sub(a);
		Point ab = b.sub(a);
		double cross = ab.cross(ac);
		
		int r = 0;
		if (cross > 0)
			r = 1;
		if (cross < 0)
			r = -1;
		
		return r;
	}
	
	public double dot(Point p)
	{
		return this.x*p.x + this.y*p.y;
	}
	
	public Point proj(Point p)
	{
		Point onto = p.norm();
		double d = this.dot(onto);
		return onto.mult(d);
	}
	
	public double cross(Point p)
	{
		return this.x*p.y - this.y*p.x;
	}

	public String toString()
	{
		return "[" + x + ", " + y + "]";
	}

	public Point neg() {
		return this.mult(-1);
	}
	
	public static Point rayToSegment(Point l1p1, Point l1p2, Point l2p1, Point l2p2)
	{
		Point l1 = l1p2.sub(l1p1);
		Point l2 = l2p2.sub(l2p1);
		
		double d = l1.cross(l2);
			if (d != 0)
			{
				 Point l3 = l2p1.sub(l1p1);
				 double r1 = l3.cross(l2) / d;
				 double r2 = l3.cross(l1) / d;
				 
				 if (r2 > 1 || r2 < 0)
					 return null;
				 
				 if (r1 < 0)
					 return null;
				 
				 Point col = l1p1.add(l1.mult(r1));
				 return col;
			}
		
		return null;
	}

	public void idiv(double d) {
		this.x /= d;
		this.y /= d;
	}

	public void imult(double m) {
		this.x *= m;
		this.y *= m;
	}

	public String intString() {
		return "[" + (int)this.x + ", " + (int)this.y + "]";
	}
}
