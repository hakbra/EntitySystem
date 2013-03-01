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
		Point mid = new Point();
		int c = 0;
		for (CoreEntity h : em.getEntity(Hero.class))
		{
			Point heroPos = em.getComponent(h, Position.class).position;
			mid.iadd(heroPos);
			c++;
		}
		mid.idiv(c);
		
		Point camDim = new Point(GLEngine.WIDTH, GLEngine.HEIGHT);
		Point halfDim = camDim.div(2);
		Point newPos = mid.sub(halfDim);
		Point worldDim = new Point(world.getDataManager().mapwidth, world.getDataManager().mapheight);
		
		if (newPos.x < 0)
			newPos.x = 0;
		if (newPos.y < 0)
			newPos.y = 0;
		
		if (newPos.x + camDim.x > worldDim.x)
			newPos.x = worldDim.x - camDim.x;
		if (newPos.y + camDim.y > worldDim.y)
			newPos.y = worldDim.y - camDim.y;
		
		if (camDim.y > worldDim.y)
			newPos.y = (worldDim.y - camDim.y)/2;
		if (camDim.x > worldDim.x)
			newPos.x = (worldDim.x - camDim.x)/2;
		
		world.camera.set(newPos);
	}
}

