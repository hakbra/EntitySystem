package helpers;

public class Color {
	public static Color BLACK = new Color(0, 0, 0);
	public static Color WHITE = new Color(1, 1, 1);
	public static Color GREEN = new Color(0, 1, 0);
	public static Color BLUE = new Color(0, 0, 1);
	public static Color RED = new Color(1, 0, 0);
	public static Color YELLOW = new Color(1, 1, 0);
	
	
	public float a, b, c;
	public float brightness;
	
	public Color(float a, float b, float c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		brightness = 1;
	}
	
	public void set(float a, float b, float c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		brightness = 1;
	}
}
