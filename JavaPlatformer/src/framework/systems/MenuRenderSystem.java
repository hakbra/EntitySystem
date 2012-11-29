package framework.systems;

import helpers.Draw;
import helpers.Point;
import framework.Component;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Button;
import framework.components.Polygon;
import framework.components.Position;


public class MenuRenderSystem extends CoreSystem{
	
	public MenuRenderSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		for (Entity e : em.get(Button.class))
		{
			Point position 		= 	em.getComponent(e, Position.class).position;
			Polygon poly 		= 	em.getComponent(e, Polygon.class);

			Draw.setColor(poly.color);
			Draw.polygon(position, poly.points);
		}
	}
}
