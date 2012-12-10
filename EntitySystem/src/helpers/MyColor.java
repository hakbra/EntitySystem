package helpers;

public class MyColor {
	public static MyColor BLACK = new MyColor(0, 0, 0);
	public static MyColor WHITE = new MyColor(1, 1, 1);
	public static MyColor GREEN = new MyColor(0, 1, 0);
	public static MyColor BLUE = new MyColor(0, 0, 1);
	public static MyColor RED = new MyColor(1, 0, 0);
	public static MyColor YELLOW = new MyColor(1, 1, 0);
	
	
	public double r, g, b;
	public double alpha;
	
	public MyColor(double a, double b, double c)
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

	public MyColor(double a, double b, double c, double d)
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
