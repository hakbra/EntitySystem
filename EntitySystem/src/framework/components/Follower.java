package framework.components;

import framework.Component;
import helpers.Point;

public class Follower extends Component{
	
	public Point target;
	public double limit;
	
	public Follower()
	{
		this.name = "Follower";
		this.limit = 400;
	}
	public Follower(int l)
	{
		this.name = "Follower";
		this.limit = l;
	}

}
