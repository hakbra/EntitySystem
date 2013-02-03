package framework.components;

import framework.CoreComponent;
import framework.CoreEntity;

public class ParentTransform extends CoreComponent{
	public CoreEntity parent;
	
	public ParentTransform(CoreEntity p)
	{
		this.parent = p;
		this.name = "ParentTransform";
	}
}
