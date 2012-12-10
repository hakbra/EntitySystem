package framework.components;

import helpers.Color;
import helpers.Draw;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import framework.Component;
import framework.Entity;
import framework.EntityManager;



public class Health extends Component{
	
	public float current;
	public float max;
	
	public Health()
	{
		this.current = 100;
		this.max = 100;
		this.name = "Health";
	}
}
