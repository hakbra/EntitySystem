package helpers;

public class Line {
	Point p1;
	Point p2;
	
	public Line(Point a, Point b)
	{
		this.p1 = a;
		this.p2 = b;
	}
	

	public Point intersection(Line other)
	{
		Point l1 = this.p2.sub(this.p1);
		Point l2 = other.p2.sub(other.p1);
		
		double d = l1.cross(l2);
			if (d != 0)
			{
				 Point l3 = other.p1.sub(this.p1);
				 double r1 = l3.cross(l2) / d;
				 double r2 = l3.cross(l1) / d;
				 
				 if (r2 > 1 || r2 < 0)
					 return null;
				 
				 if (r1 < 0 || r1 > 1)
					 return null;
				 
				 Point col = this.p1.add(l1.mult(r1));
				 return col;
			}
		
		return null;
	}

	public double dist(Point p)
	{
		return p.dist(pointOnLine(p));
	}

	public Point pointOnLine(Point p)
	{
		Point ac = p.sub(this.p1);
		Point ab = this.p2.sub(this.p1);
		
		double r = ac.dot(ab) / ab.mag();
		
		if (r < 0)
			r = 0;
		if (r > 1)
			r = 1;
		
		return this.p1.add(ab.mult(r));
	}
}
