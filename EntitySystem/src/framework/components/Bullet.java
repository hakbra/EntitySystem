package framework.components;

import framework.CoreComponent;
import framework.CoreEntity;

public class Bullet  extends CoreComponent{

	public CoreEntity parent;
	
	public Bullet(CoreEntity p)
	{
		this.parent = p;
	}
}
