package framework.systems;

import helpers.Point;
import engine.GLEngine;
import framework.CoreSystem;
import framework.CoreEntity;
import framework.World;
import framework.components.Hero;
import framework.components.CollisionPolygon;
import framework.components.Position;
import framework.managers.EntityManager;

public class CameraSystem extends CoreSystem{


	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		CoreEntity cam = em.getByStringID("camera");
		Point pos = em.getComponent(cam, Position.class).position;
		CollisionPolygon poly = em.getComponent(cam, CollisionPolygon.class);
		
		Point mid = new Point();
		int c = 0;
		for (CoreEntity h : em.getEntity(Hero.class))
		{
			Point heroPos = em.getComponent(h, Position.class).position;
			mid.iadd(heroPos);
			c++;
		}
		mid.idiv(c);
		
		Point dim = poly.dim;
		Point halfDim = dim.div(2);
		Point newPos = mid.sub(halfDim);
		Point worldDim = new Point(world.getDataManager().mapwidth, world.getDataManager().mapheight);
		
		if (newPos.x < 0)
			newPos.x = 0;
		if (newPos.y < 0)
			newPos.y = 0;
		
		if (newPos.x + dim.x > worldDim.x)
			newPos.x = worldDim.x - dim.x;
		if (newPos.y + dim.y > worldDim.y)
			newPos.y = worldDim.y - dim.y;
		
		if (dim.y > worldDim.y)
			newPos.y = (worldDim.y - dim.y)/2;
		if (dim.x > worldDim.x)
			newPos.x = (worldDim.x - dim.x)/2;
		
		pos.set(newPos);
	}
}

