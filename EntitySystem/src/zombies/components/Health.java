package zombies.components;


import java.util.Random;

import org.lwjgl.opengl.GL11;

import zombies.utils.Draw;


import framework.CoreComponent;
import framework.CoreEntity;
import framework.managers.EntityManager;
import framework.utils.Color;



public class Health extends CoreComponent{
	
	public float current;
	public float max;
	
	public Health()
	{
		this.current = 100;
		this.max = 100;
		this.name = "Health";
	}
	public Health(float m)
	{
		this.current = m;
		this.max = m;
		this.name = "Health";
	}
}
