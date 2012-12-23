package framework;

import java.util.*;

public class Entity implements Comparable{
	
	public Layer layer;
	public String name;
	public ArrayList<Component> components;
	
	public Entity()
	{
		components = new ArrayList<Component>();
		layer = Layer.NOT;
	}
	
	public String toString()
	{
		return name;
	}

	@Override
	public int compareTo(Object o) {
		return ((Entity) o).layer.num.compareTo(this.layer.num) * -1;
	}
}
