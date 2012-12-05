package framework.systems;

import helpers.Point;
import helpers.Time;

import java.util.PriorityQueue;

import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Circle;
import framework.components.Hero;
import framework.components.Obstacle;
import framework.components.Pathfinder;
import framework.components.Pathfinder.Node;
import framework.components.Polygon;
import framework.components.Position;

public class PathSystem extends CoreSystem{

	public PathSystem(EntityManager em)
	{
		super(em);
	}

	@Override
	public void run(EntityManager em)
	{
		Entity pfEntity = em.get(Pathfinder.class).remove(0);
		Pathfinder pf = em.getComponent(pfEntity, Pathfinder.class);


		if (!pf.updated)
		{
			for (Entity e : em.getAll(Obstacle.class, Polygon.class))
			{
				Polygon poly = em.getComponent(e, Polygon.class);
				Point pos = em.getComponent(e, Position.class).position;

				for (int i = 0; i < pf.width / pf.step; i++)
					for (int j = 0; j < pf.height / pf.step; j++)
					{
						Point p = new Point(i*pf.step, j*pf.step);
						pf.map[i][j].pos = p;
						pf.map[i][j].i = new Point(i, j);
						double dist = p.dist(poly.getClosest(pos, p));

						if (poly.isInside(pos, p))
							pf.map[i][j].blocked = true;
						if (dist < pf.map[i][j].dist)
							pf.map[i][j].dist = dist;
					}
			}
			for (Entity e : em.getAll(Obstacle.class, Circle.class))
			{
				if (em.hasComponent(e, Hero.class))
					continue;
				
				Circle circle = em.getComponent(e, Circle.class);
				Point pos = em.getComponent(e, Position.class).position;

				for (int i = 0; i < pf.width / pf.step; i++)
					for (int j = 0; j < pf.height / pf.step; j++)
					{
						double dist = pf.map[i][j].pos.dist(circle.getClosest(pos, pf.map[i][j].pos));

						if (circle.isInside(pos, pf.map[i][j].pos))
							pf.map[i][j].blocked = true;
						if (dist < pf.map[i][j].dist)
							pf.map[i][j].dist = dist;
					}
			}

			pf.updated = true;
		}

		long now = Time.getTime();
		pf.max = 0;

		PriorityQueue<Node> queue = new PriorityQueue<Pathfinder.Node>();
		
		for (Entity hero : em.get(Hero.class))
		{
			Point pos = em.getComponent(hero, Position.class).position;
			Node n = pf.getNode(pos);
			if (n != null)
			{
				n.value = 0.0;
				queue.add(n);
			}
		}



		while (queue.size() > 0)
		{
			Node current = queue.remove();

			int ix = (int) current.i.x;
			int iy = (int) current.i.y;

			for (int i = ix-1; i <= ix+1; i++)
			{
				for (int j = iy-1; j <= iy+1; j++)
				{
					if (i == ix && j == iy)
						continue;

					if (pf.isLegal(i, j))
					{					
						if (pf.map[i][j].blocked)
							continue;
						
						if (pf.map[i][j].done == now)
							continue;

						if (pf.map[i][j].visited != now)
						{
							pf.map[i][j].value = 1000.0;
							pf.map[i][j].visited = now;
						}

						double newDist = current.value + Math.sqrt( (ix-i)*(ix-i) + (iy-j)*(iy-j) );
						if (pf.map[i][j].value > newDist )
						{
							if (queue.contains(pf.map[i][j]))
								queue.remove(pf.map[i][j]);

							pf.map[i][j].value = newDist;

							queue.add(pf.map[i][j]);
						}

					}
				}
			}

			current.done = now;
			if (current.value > pf.max)
				pf.max = current.value;

		}

		//pf.render();


	}
}
