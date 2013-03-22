package framework;

public abstract class CoreSystem{
	protected World world;
	public boolean enabled;

	public  void init(){}
	public  void run(){}
	public  void stop(){}
	
	public CoreSystem()
	{
		this.enabled = true;
	}
	
	public void disable()
	{
		enabled = false;
	}
	
	public void enable()
	{
		enabled = true;
	}
	
	public void toggle()
	{
		if (enabled)
			disable();
		else
			enable();
	}
}
