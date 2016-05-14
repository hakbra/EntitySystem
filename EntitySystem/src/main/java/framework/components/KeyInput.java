package framework.components;

import framework.CoreComponent;

public class KeyInput extends CoreComponent{
	
	public int left;
	public int right;
	public int up;
	public int down;
	public int shoot;
	public int pickup;
	
	public KeyInput(int l, int r, int u, int d, int s, int p)
	{
		this.left = l;
		this.right = r;
		this.up = u;
		this.down = d;
		this.shoot = s;
		this.pickup = p;
		this.name = "KeyInput";
	}

}
