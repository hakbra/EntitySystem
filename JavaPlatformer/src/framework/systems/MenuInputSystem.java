package framework.systems;

import helpers.Point;

import org.lwjgl.input.Mouse;

import framework.Component;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Button;
import framework.components.Polygon;
import framework.components.Position;

public class MenuInputSystem extends CoreSystem{

	public MenuInputSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		Point mouse = null;
		
        if (Mouse.isButtonDown(0))
	    	mouse = new Point(Mouse.getX(), Mouse.getY());
	    	
	    if (mouse == null)
	    	return;
		
		for (Entity e : em.get(Button.class))
		{
			Point position 		= 	em.getComponent(e, Position.class).position;
			Polygon poly 		= 	em.getComponent(e, Polygon.class);
			Button button 		= 	em.getComponent(e, Button.class);
			
			if (poly.isInside(position, mouse))
				em.state = button.state;
		}
	}
}
