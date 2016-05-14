package framework.enums;

public enum LayerEnum {
	NOT(-1), GROUND(0), ITEM(1), MOVER(2), LIGHT(3), OBSTACLE(4), HUD(5), TEXT(6);
	
	public Integer num;
	
	private LayerEnum(int n)
	{
		this.num = n;
	}
}
