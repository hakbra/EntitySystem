package framework.systems;

import helpers.Color;
import helpers.Point;
import helpers.State;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.Follower;
import framework.components.Health;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.TextureComp;
import framework.components.Velocity;
import framework.components.Zombie;

public class MouseInputSystem extends CoreSystem{

	public MouseInputSystem(EntityManager em)
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

		for (Entity e : em.getEntity(Button.class))
		{
			Point position 		= 	em.getComponent(e, Position.class).position;
			Polygon poly 		= 	em.getComponent(e, Polygon.class);
			Button button = 	em.getComponent(e, Button.class);
			
			if (poly.isInside(position, mouse))
			{
				if (button.type == "Zombie")
				{
					ArrayList<Point> spawns = new ArrayList<Point>();
					/*
					spawns.add(  new Point(50, 50));
					spawns.add(  new Point(GLEngine.WIDTH - 50, 50));
					spawns.add(  new Point(50, GLEngine.HEIGHT - 50));
					spawns.add(  new Point(GLEngine.WIDTH - 50, GLEngine.HEIGHT - 50));

					spawns.add(  new Point(GLEngine.WIDTH / 2, 50));
					spawns.add(  new Point(GLEngine.WIDTH / 2, GLEngine.HEIGHT - 50));
					spawns.add(  new Point(GLEngine.WIDTH - 50, GLEngine.HEIGHT / 2));
					*/
					spawns.add(  new Point(50, GLEngine.HEIGHT / 2));
					
					for (Point p : spawns)
					{
						Entity zombie = new Entity();
						zombie.name = "Zombie";
						em.addComponent(zombie, new Zombie());
						em.addComponent(zombie, new Circle(20, Color.YELLOW));
						em.addComponent(zombie, new Position(p));
						em.addComponent(zombie, new Velocity(new Point(0, 0)));
						em.addComponent(zombie, new Health());
						em.addComponent(zombie, new Follower());
						em.addComponent(zombie, new Damage(1, 200));
						em.addComponent(zombie, new Obstacle());
						em.addComponent(zombie, new Collider());
						em.addComponent(zombie, new Angle(0));
						em.addComponent(zombie, new TextureComp("zombie.png"));
					}
				}
				else if (button.type == "Screen")
				{
					GLEngine.switchFullscreen();
				}
				else if (button.type == "Play")
				{
					em.setState(State.RUN);
				}
				else if (button.type == "Exit")
				{
					em.setState(State.EXIT);
				}
				else if (button.type == "Menu")
				{
					em.setState(State.MENU);
				}
			}
		}
	}
}
