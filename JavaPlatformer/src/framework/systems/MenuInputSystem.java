package framework.systems;

import helpers.Color;
import helpers.Point;
import helpers.State;

import org.lwjgl.input.Mouse;

import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.Follower;
import framework.components.Health;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.StateButton;
import framework.components.StringButton;
import framework.components.Velocity;
import framework.components.Zombie;

public class MenuInputSystem extends CoreSystem{

	public MenuInputSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		Point mouse = null;
		
		while (Mouse.next())
		    if (Mouse.getEventButtonState())
		    	mouse = new Point(Mouse.getEventX(), Mouse.getEventY());
	    	
	    if (mouse == null)
	    	return;
		
		for (Entity e : em.get(StateButton.class))
		{
			Point position 		= 	em.getComponent(e, Position.class).position;
			Polygon poly 		= 	em.getComponent(e, Polygon.class);
			StateButton button 		= 	em.getComponent(e, StateButton.class);
			
			if (poly.isInside(position, mouse))
				em.setState(button.state);
		}

		for (Entity e : em.get(StringButton.class))
		{
			Point position 		= 	em.getComponent(e, Position.class).position;
			Polygon poly 		= 	em.getComponent(e, Polygon.class);
			StringButton button = 	em.getComponent(e, StringButton.class);
			
			if (poly.isInside(position, mouse))
			{
				if (button.type == "Zombie")
				{
					for (int i = 0; i < 4; i++)
					{
						Entity zombie = new Entity();
						zombie.name = "Zombie";
						em.addComponent(zombie, new Zombie());
						em.addComponent(zombie, new Circle(20, Color.YELLOW));
						em.addComponent(zombie, new Position(new Point(200, 350+i)));
						em.addComponent(zombie, new Velocity(new Point(0, 0)));
						em.addComponent(zombie, new Health());
						em.addComponent(zombie, new Follower());
						em.addComponent(zombie, new Damage(5, 1000));
						em.addComponent(zombie, new Obstacle());
						em.addComponent(zombie, new Collider());
					}
				}
			}
		}
	}
}
