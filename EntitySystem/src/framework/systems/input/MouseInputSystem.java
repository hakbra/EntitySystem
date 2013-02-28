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

	
	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		
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
					world.setState(StateEnum.GAME_MENU);
				}
				else if (button.type == "Exit")
				{
					world.setState(StateEnum.EXIT);
				}
				else if (button.type == "Play")
				{
					world.setStateAndClear(StateEnum.LEVEL1);
					Level1State.init(world);
				}
				else if (button.type == "Resume")
				{
					world.setState(StateEnum.LEVEL1);
				}
				else if (button.type == "Restart")
				{
					world.setStateAndClear(StateEnum.LEVEL1);
					Level1State.init(world);
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
