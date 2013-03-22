package framework;

import framework.enums.LayerEnum;

public class CoreComponent implements Comparable<CoreComponent> {
	
	protected String name;
	public World world;
	public CoreEntity parent;
	public LayerEnum layer = LayerEnum.NOT;

	public CoreComponent setLayer(LayerEnum l)
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
		return co.layer.compareTo(this.layer) * -1;
	}

}
