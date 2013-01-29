package framework;

import java.util.*;

import framework.enums.LayerEnum;

public class CoreEntity implements Comparable{
	
	public World world;
	public LayerEnum layer;
	public String name;
	public ArrayList<CoreComponent> components;
	
	public CoreEntity()
	{
		components = new ArrayList<CoreComponent>();
		layer = LayerEnum.NOT;
	}
	
	public String toString()
	{
		return name;
	}

	@Override
	public int compareTo(Object o) {
		return ((CoreEntity) o).layer.num.compareTo(this.layer.num) * -1;
	}
}
