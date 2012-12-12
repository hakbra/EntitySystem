package framework.systems;

import helpers.Point;
import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Hero;
import framework.components.Position;

public class CameraSystem extends CoreSystem{

	public CameraSystem(EntityManager em)
	{
		super(em);
	}

	@Override
	public void run(EntityManager em)
	{
		Entity world = em.getByStringID("world");
		Point pos = em.getComponent(world, Position.class).position;
		
		Point mid = new Point();
		int c = 0;
		
		for (Entity h : em.getEntity(Hero.class))
		{
			Point heroPos = em.getComponent(h, Position.class).position;
			mid.isub(heroPos);
			c++;
		}
		
		mid.idiv(c);
		mid.iadd( new Point(GLEngine.WIDTH/2, GLEngine.HEIGHT/2));
		pos.set(mid);
	}
}
