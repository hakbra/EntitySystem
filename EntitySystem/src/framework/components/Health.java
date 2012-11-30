package framework.components;

import java.util.Random;

import framework.Component;



public class Health extends Component{
	
	public float current;
	public float max;
	
	public Health()
	{
		this.current = 100;
		this.max = 100;
		this.name = "Health";
	}

}
