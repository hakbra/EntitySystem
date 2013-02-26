package framework.systems.input;

import framework.CoreSystem;
import framework.World;
import framework.managers.EntityManager;




public class KeyInputSystem extends CoreSystem{
	
	public static float s = 2f;

	public KeyInputSystem(World w)
	{
		super(w);
	}
	@Override
	public void run(EntityManager em)
	{
	}
}
