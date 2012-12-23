package framework;

public enum Layer {
	NOT(-1), GROUND(0), ITEM(1), MOVER(2), LIGHT(3), OBSTACLE(4), HUD(5);
	
	public Integer num;
	
	private Layer(int n)
	{
		this.num = n;
	}
}
