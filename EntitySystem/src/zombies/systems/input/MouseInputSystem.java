package zombies.systems.input;


import org.lwjgl.input.Mouse;

import zombies.components.Button;
import zombies.components.CollisionPolygon;
import zombies.states.Level1State;
import zombies.systems.EmitterSystem;
import zombies.systems.LightSystem;
import zombies.systems.PhysicsSystem;
import zombies.systems.TimerSystem;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.DynEnum;
import framework.engine.GLEngine;
import framework.managers.EntityManager;
import framework.utils.Point;

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
					world.setState(DynEnum.at("state").get("game_menu"));
				}
				else if (button.type == "Exit")
				{
					world.setState(DynEnum.at("state").get("exit"));
				}
				else if (button.type == "Play")
				{
					world.setStateAndClear(DynEnum.at("state").get("level1"));
					Level1State.init(world);
				}
				else if (button.type == "Resume")
				{
					world.setState(DynEnum.at("state").get("level1"));
				}
				else if (button.type == "Restart")
				{
					world.setStateAndClear(DynEnum.at("state").get("level1"));
					Level1State.init(world);
				}
				else if (button.type == "Light")
				{
					world.getSystemManager().toggleSystem(LightSystem.class);
				}
				else if (button.type == "Pause")
				{
					world.getSystemManager().toggleSystem(PhysicsSystem.class);
					world.getSystemManager().toggleSystem(PlayerInputSystem.class);
					world.getSystemManager().toggleSystem(TimerSystem.class);
					world.getSystemManager().toggleSystem(EmitterSystem.class);
				}
				else
					System.out.println("Button " + button.type + " not recognised");
			}
			else
				button.active = false;
		}
	}
}
