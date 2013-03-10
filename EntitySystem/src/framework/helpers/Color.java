package framework.helpers;

public class Color {
	public static Color BLACK = new Color(0, 0, 0);
	public static Color WHITE = new Color(1, 1, 1);
	public static Color GREEN = new Color(0, 1, 0);
	public static Color BLUE = new Color(0, 0, 1);
	public static Color RED = new Color(1, 0, 0);
	public static Color YELLOW = new Color(1, 1, 0);
	
	
	public double r, g, b;
	public double alpha;
	
	public Color(double a, double b, double c)
	{
		this.r = a;
		this.g = b;
		this.b = c;
		alpha = 1;
	}
	
	public void set(double a, double b, double c)
	{
		this.r = a;
		this.g = b;
		this.b = c;
		alpha = 1;
	}

	public Color(double a, double b, double c, double d)
	{
		this.r = a;
		this.g = b;
		this.b = c;
		this.alpha = d;
	}
	
	public void set(double a, double b, double c, double d)
	{
		this.r = a;
		this.g = b;
		this.b = c;
		this.alpha = d;
	}
}
