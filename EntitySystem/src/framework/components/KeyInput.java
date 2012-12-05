package framework.components;

import framework.Component;

public class KeyInput extends Component{
	
	public int left;
	public int right;
	public int up;
	public int down;
	public int shoot;
	
	public KeyInput(int l, int r, int u, int d, int s)
	{
		this.left = l;
		this.right = r;
		this.up = u;
		this.down = d;
		this.shoot = s;
		this.name = "KeyInput";
	}

}
