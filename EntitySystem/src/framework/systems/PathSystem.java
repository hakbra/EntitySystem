package framework.systems;

import helpers.Point;
import helpers.Time;

import java.util.PriorityQueue;

import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Circle;
import framework.components.Hero;
import framework.components.Obstacle;
import framework.components.Pathfinder;
import framework.components.Pathfinder.Node;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Velocity;
import framework.components.Zombie;

public class PathSystem extends CoreSystem{

	public PathSystem(World w)
	{
		super(w);
	}

	@Override
	public void run(EntityManager em)
	{
		Entity cam = em.getByStringID("camera");
		Pathfinder pf = em.getComponent(cam, Pathfinder.class);

		long now = Time.getTime();
		pf.update = now;
		for (Entity e : em.getEntityAll(Obstacle.class, Polygon.class))
		{
			Polygon poly = em.getComponent(e, Polygon.class);
			Point pos = em.getComponent(e, Position.class).position;
			
			if (!poly.inverted)
				pf.mask(poly, pos, now);
		}


		PriorityQueue<Node> queue = new PriorityQueue<Pathfinder.Node>();

		for (Entity hero : em.getEntity(Hero.class))
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
						if (pf.map[i][j].done == now)
							continue;

						if (pf.map[i][j].visited != now)
						{
							pf.map[i][j].value = 10000.0;
							pf.map[i][j].visited = now;
							pf.map[i][j].weight = 0;
							pf.map[i][j].blocked = false;
							pf.map[i][j].prev = null;
						}

						double newDist = pf.map[i][j].pos.dist(current.pos) + current.value;// + pf.map[i][j].weight;
						if (pf.map[i][j].value > newDist )
						{
							if (queue.contains(pf.map[i][j]))
								queue.remove(pf.map[i][j]);

							pf.map[i][j].value = newDist;
							pf.map[i][j].prev = current;

							queue.add(pf.map[i][j]);
						}

					}
				}
			}

			current.done = now;
		}
	}
}
