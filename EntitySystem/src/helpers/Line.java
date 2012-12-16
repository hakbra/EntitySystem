package helpers;

public class Line {
	Point p1;
	Point p2;

	public Line(Point a, Point b)
	{
		this.p1 = a;
		this.p2 = b;
	}


	public Point intersectionLine(Line other)
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

	public Point intersectionRay(Line other)
	{
		Point l1 = this.p2.sub(this.p1);
		Point l2 = other.p2.sub(other.p1);

		double d = l1.cross(l2);
		if (d != 0)
		{
			Point l3 = other.p1.sub(this.p1);
			double r1 = l3.cross(l2) / d;
			double r2 = l3.cross(l1) / d;

			if (r2 < 0)
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

	public Point pointOnCircle(Point C, double r, boolean first)
	{
		Point E = this.p1;
		Point L = this.p2;
		Point d = L.sub(E);
		Point f = E.sub(C);

		double a = d.dot(d);
		double b = 2*f.dot(d);
		double c = f.dot(f) - r*r;

		double disc = b*b-4*a*c;

		if (disc < 0)
			return null;
		if (disc == 0)
		{
			double t = -b / (2*a);

			if (t < 0 || t > 1)
				return null;

			return E.add(d.mult(t));
		}
		else
		{
			disc = Math.sqrt(disc);

			double t1 = (-b - disc)/ (2*a);
			double t2 = (-b + disc)/ (2*a);

			if ((t1 < 0 || t1 > 1) && first)
				return null;
			else if (first)
				return E.add(d.mult(t1));

			if ((t2 < 0 || t2 > 1) && !first)
				return null;
			else if (!first)
				return E.add(d.mult(t2));

			return null;
		}


	}
}
