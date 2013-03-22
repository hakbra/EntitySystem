package framework;


public class CoreComponent implements Comparable<CoreComponent> {
	
	protected String name;
	public World world;
	public CoreEntity parent;
	public int layer = DynEnum.at("layer").get("null");

	public CoreComponent setLayer(int l)
	{
		this.layer = l;
		return this;
	}
	
	public String toString()
	{
		return name;
	}
	
	public void render()
	{
		
	}

	@Override
	public int compareTo(CoreComponent co) {
		return this.layer - co.layer;
	}

}
