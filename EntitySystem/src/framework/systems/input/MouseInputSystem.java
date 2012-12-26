package framework.systems.input;

import helpers.Point;

import java.util.Random;

import org.lwjgl.input.Mouse;

import states.Level1State;
import states.MessageState;
import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.Layer;
import framework.State;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.Follower;
import framework.components.Health;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Velocity;
import framework.components.Zombie;

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

		for (Entity e : em.getEntity(Button.class))
		{
			Polygon poly 		= 	em.getComponent(e, Polygon.class);
			Button button = 	em.getComponent(e, Button.class);
			
			if (poly.isInside(mouse))
			{
				button.active = true;

			    if (!Mouse.getEventButtonState())
			    	continue;
			    	
				if (button.type == "Zombies")
				{
					Random r = new Random();
					for (int i = 0; i < 20; i++)
					{
						Entity zombie = new Entity();
						zombie.name = "Zombie";
						zombie.layer = Layer.MOVER;
						em.addComponent(zombie, new Zombie());
						em.addComponent(zombie, new Circle(20));
						em.addComponent(zombie, new Position(new Point(r.nextInt(GLEngine.WIDTH), r.nextInt(GLEngine.HEIGHT))));
						em.addComponent(zombie, new Velocity(new Point(0, 0)));
						em.addComponent(zombie, new Health());
						em.addComponent(zombie, new Follower());
						em.addComponent(zombie, new Damage(1, 200));
						em.addComponent(zombie, new Obstacle());
						em.addComponent(zombie, new Collider(4));
						em.addComponent(zombie, new Angle(0));
						em.addComponent(zombie, new AngleSpeed(0));
						em.addComponent(zombie, new Tex("zombie.png"));
					}
				}
				else if (button.type == "Screen")
				{
					GLEngine.switchFullscreen();
				}
				else if (button.type == "Menu")
				{
					world.pushState(State.GAME_MENU);
				}
				else if (button.type == "Exit")
				{
					world.pushState(State.EXIT);
				}
				else if (button.type == "Play")
				{
					Level1State.init(world);
					world.pushState(State.LEVEL1);
					
					MessageState.init(world, "PLAYER ONE READY");
					world.pushState(State.MESSAGE);
					
				}
				else if (button.type == "Resume")
				{
					world.popState();
				}
				else if (button.type == "Restart")
				{
					world.popState();
					Level1State.init(world);
					world.pushState(State.LEVEL1);
				}
			}
			else
				button.active = false;
		}
	}
}
