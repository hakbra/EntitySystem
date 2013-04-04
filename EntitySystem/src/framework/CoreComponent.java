package framework;


public abstract class CoreComponent{
	
	protected String name;
	public World world;
	public CoreEntity parent;
	public int layer = DynEnum.at("layer").get("null");
	
	public String toString()
	{
		return name;
	}

}
