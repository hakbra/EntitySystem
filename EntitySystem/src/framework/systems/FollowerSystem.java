package framework.systems;

import helpers.Point;
import helpers.Time;

import java.util.PriorityQueue;

import framework.CoreEntity;
import framework.CoreSystem;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.CollisionPolygon;
import framework.components.Follower;
import framework.components.Hero;
import framework.components.Obstacle;
import framework.components.Position;
import framework.components.Velocity;
import framework.managers.DataManager;
import framework.managers.EntityManager;
import framework.misc.Pathfinder;
import framework.misc.Pathfinder.Node;

public class FollowerSystem extends CoreSystem{
	Pathfinder pf;

	@Override
	public void init()
	{
		DataManager dm = world.getDataManager();
		pf = new Pathfinder(world.mapdim, 10);
	}

	private void update()
	{
		EntityManager em = world.getEntityManager();

		long now = Time.getTime();
		pf.update = now;
		for (CoreEntity e : em.getEntityAll(Obstacle.class, CollisionPolygon.class))
		{
			CollisionPolygon poly = em.getComponent(e, CollisionPolygon.class);
			Point pos = em.getComponent(e, Position.class).position;
			if (!poly.inverted)
				pf.mask(poly, pos, now);
		}

		PriorityQueue<Node> queue = new PriorityQueue<Pathfinder.Node>();

		for (CoreEntity hero : em.getEntity(Hero.class))
		{
			Point pos = em.getComponent(hero, Position.class).position;
			Node n = pf.getNode(pos);
			if (n == null)
				return;
			
			n.value = 0.0;
			queue.add(n);
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
							pf.map[i][j].blocked = false;
							pf.map[i][j].prev = null;
						}

						double newDist = pf.map[i][j].pos.dist(current.pos) + current.value;
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

	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();

		this.update();

		for (CoreEntity e : em.getEntityAll(Follower.class))
		{
			Follower follower = em.getComponent(e, Follower.class);
			Point pos = em.getComponent(e, Position.class).position;
			Point vel = em.getComponent(e, Velocity.class).dir;
			Angle a = em.getComponent(e, Angle.class);
			AngleSpeed as = em.getComponent(e, AngleSpeed.class);

			Point dir = pf.getDir(pos);

			double oldAng = a.angle;
			double newAng = dir.angle();
			double delta = dir.angle( new Point(oldAng));

			double m = Math.abs(dir.norm().dot( new Point(oldAng).norm() ));

			if (delta > 0)
				as.speed = 6;
			else if (delta < 0)
				as.speed = -6;
			else
				as.speed = 0;

			if (follower.limit <= 0 || (follower.limit > 0 && dir.len() < follower.limit))
			{
				vel.set(dir);
				follower.limit = -1;
			}
			else
				vel.set(new Point());
		}
	}
}