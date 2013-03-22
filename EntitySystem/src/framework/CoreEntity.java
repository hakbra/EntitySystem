package framework;

import java.util.ArrayList;

public class CoreEntity{
	
	public World world;
	public String name;
	public ArrayList<CoreComponent> components;
	
	public CoreEntity()
	{
		components = new ArrayList<CoreComponent>();
	}
	
	public String toString()
	{
		return name;
	}
}
