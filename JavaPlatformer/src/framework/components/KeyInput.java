package framework.components;

import framework.Component;

public class KeyInput extends Component{
	
	public int left;
	public int right;
	public int up;
	public int down;
	
	public KeyInput(int l, int r, int u, int d)
	{
		this.left = l;
		this.right = r;
		this.up = u;
		this.down = d;
		this.name = "KeyInput";
	}

}
