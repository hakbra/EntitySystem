package framework.systems;

import helpers.Color;
import helpers.Draw;
import helpers.Point;
import framework.Component;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.StateButton;
import framework.components.Circle;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Polygon;
import framework.components.Position;


public class GameRenderSystem extends CoreSystem{
	
	public GameRenderSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		for (Entity e : em.get(Circle.class))
		{
			
			Point position 		= 	em.getComponent(e, Position.class).position;
			Circle circle 		= 	em.getComponent(e, Circle.class);
			
			Draw.setColor(circle.color);
			Draw.circle(circle.radius, position);
			
			if (em.hasComponents(e, Gun.class, Angle.class))
			{
				Gun gun = em.getComponent(e, Gun.class);
				float angle = em.getComponent(e, Angle.class).angle;
				
				Point dir = new Point(angle).mult(circle.radius);
				
				Draw.setColor(Color.BLACK);
				Draw.triangle(position, position.add(dir.rot(gun.spread)), position.add(dir.rot(-gun.spread)));
			}

			if (em.hasComponent(e, Health.class))
			{
				Health health = em.getComponent(e, Health.class);
				float d = health.current / health.max;

				Draw.setColor(Color.RED);
				Draw.circle(circle.radius * (1-d*d), position);
			}
		}
		for (Entity e : em.get(Polygon.class))
		{
			Point position 		= 	em.getComponent(e, Position.class).position;
			Polygon poly 		= 	em.getComponent(e, Polygon.class);
			
			Draw.setColor(poly.color);
			Draw.polygon(position, poly.points);
		}
	}
}
