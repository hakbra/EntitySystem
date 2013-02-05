package framework;

import java.util.*;

import framework.enums.LayerEnum;

public class CoreEntity{
	
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
}
