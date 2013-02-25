package framework.systems.input;

import helpers.Point;

import org.lwjgl.input.Mouse;

import zombies.states.Level1State;
import zombies.states.Level2State;
import engine.GLEngine;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Button;
import framework.components.CollisionPolygon;
import framework.enums.StateEnum;
import framework.managers.EntityManager;
import framework.systems.LightSystem;

public class MouseInputSystem extends CoreSystem{

	public MouseInputSystem(World w)
	{
		super(w);
	}
	
	@Override
	public void run(EntityManager em)
	{
		Point mouse = null;
		
		while (Mouse.next())
		    mouse = new Point(Mouse.getEventX(), Mouse.getEventY());
	    	
	    if (mouse == null)
	    	return;

		for (CoreEntity e : em.getEntity(Button.class))
		{
			CollisionPolygon poly 		= 	em.getComponent(e, CollisionPolygon.class);
			Button button = 	em.getComponent(e, Button.class);
			
			if (poly.isInside(mouse))
			{
				button.active = true;

			    if (!Mouse.getEventButtonState())
			    	continue;
			    	
				if (button.type == "Screen")
				{
					GLEngine.switchFullscreen();
				}
				else if (button.type == "Menu")
				{
					world.pushState(StateEnum.GAME_MENU);
				}
				else if (button.type == "Exit")
				{
					world.pushState(StateEnum.EXIT);
				}
				else if (button.type == "Play")
				{
					Level1State.init(world);
					world.pushState(StateEnum.LEVEL1);
					
				}
				else if (button.type == "Resume")
				{
					world.popState();
				}
				else if (button.type == "Restart")
				{
					world.popState();
					Level1State.init(world);
					world.pushState(StateEnum.LEVEL1);
				}
				else if (button.type == "Light")
				{
					world.getSystemManager().toggleSystem(LightSystem.class);
				}
			}
			else
				button.active = false;
		}
	}
}
