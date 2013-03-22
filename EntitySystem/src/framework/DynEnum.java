package framework;
import java.util.HashMap;
import java.util.Map;

public class DynEnum {
	
	private static Map<String, DynEnum> enums = new HashMap();
	private Map<String, Integer> values = new HashMap();
	private int counter;
	String name;
	
	public DynEnum(String n)
	{
		this.name = n;
	}
	
	private static DynEnum init(String name)
	{
		DynEnum newEnum = new DynEnum(name);
		enums.put(name, newEnum);
		return newEnum;
	}
	
	public static DynEnum at(String name)
	{
		DynEnum e = enums.get(name);
		if (e == null)
			return init(name);
		return e;
	}
	
	public int get(String valname)
	{
		Integer value = values.get(valname);
		if (value == null)
			throw new RuntimeException("Enum " + name + " does not have value " + valname + "!");
		
		return value;
	}
	
	public void add(String valname)
	{
		if (values.get(valname) != null)
			throw new RuntimeException("Enum " + name + " does already have value " + valname + "!");
		
		values.put(valname, counter);
		counter++;
	}
	public void addAll(String... values)
	{
		for (String s : values)
			add(s);
	}
}