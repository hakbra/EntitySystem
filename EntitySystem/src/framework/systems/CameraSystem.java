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

	public CameraSystem(World w)
	{
		super(w);
	}

	@Override
	public void run(EntityManager em)
	{
		CoreEntity cam = em.getByStringID("camera");
		Point pos = em.getComponent(cam, Position.class).position;
		CollisionPolygon poly = em.getComponent(cam, CollisionPolygon.class);
		
		Point mid = new Point();
		int c = 0;
		
		for (CoreEntity h : em.getEntity(Hero.class))
		{
			Point heroPos = em.getComponent(h, Position.class).position;
			mid.isub(heroPos);
			c++;
		}
		
		mid.idiv(c);
		mid = mid.neg();

		mid.isub( new Point(GLEngine.WIDTH/2, GLEngine.HEIGHT/2));
		
		if (mid.x < poly.min.x)
			mid.x = poly.min.x;
		if (mid.x > poly.max.x)
			mid.x = poly.max.x;

		if (mid.y < poly.min.y)
			mid.y = poly.min.y;
		if (mid.y > poly.max.y)
			mid.y = poly.max.y;
		
		pos.set(mid);
	}
}
