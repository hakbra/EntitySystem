package framework.components;

import framework.CoreComponent;
import framework.CoreEntity;

public class Bullet  extends CoreComponent{

	public CoreEntity owner;
	
	public Bullet(CoreEntity p)
	{
		this.owner = p;
	}
}
