package framework.components;

import helpers.Point;
import framework.CoreComponent;

public class ZombieSpawner extends CoreComponent{

	public long last;
	public int delay;
	public int counter;
	
	public ZombieSpawner(int d)
	{
		this.delay = d;
		this.last = 0;
		this.counter = 0;
		this.name = "ZombieSpawner";
	}
}
