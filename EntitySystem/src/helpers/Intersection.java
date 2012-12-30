package helpers;

import framework.CoreEntity;


public class Intersection
{
	public CoreEntity a;
	public CoreEntity b;
	public Point poi;
	public boolean inside;

	public Intersection(CoreEntity e1, CoreEntity e2, Point p, boolean i)
	{
		this.a = e1;
		this.b = e2;
		this.poi = p;
		this.inside = i;
	}
}