package framework;

import framework.enums.LayerEnum;

public class CoreComponent implements Comparable {
	
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
	public int compareTo(Object o) {
		return ((CoreComponent) o).layer.num.compareTo(this.layer.num) * -1;
	}

}
