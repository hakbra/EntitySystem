package helpers;

public class Color {
	public static Color BLACK = new Color(0, 0, 0);
	public static Color WHITE = new Color(1, 1, 1);
	public static Color GREEN = new Color(0, 1, 0);
	public static Color BLUE = new Color(0, 0, 1);
	public static Color RED = new Color(1, 0, 0);
	public static Color YELLOW = new Color(1, 1, 0);
	
	
	public float a, b, c;
	
	public Color(float a, float b, float c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public void mult(double m)
	{
		this.a *= m;
		this.b *= m;
		this.c *= m;
	}
}
