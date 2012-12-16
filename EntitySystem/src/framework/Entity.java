package framework;

import java.util.*;

public class Entity {
	
	public String name;
	public ArrayList<Component> components;
	
	public Entity()
	{
		components = new ArrayList<Component>();
	}
	
	public String toString()
	{
		return name;
	}
}
