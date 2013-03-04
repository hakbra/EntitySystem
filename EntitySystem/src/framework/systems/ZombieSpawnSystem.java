package framework.systems;

import helpers.Point;
import helpers.Time;

import java.util.Random;

import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Acceleration;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Collider;
import framework.components.CollisionCircle;
import framework.components.Damage;
import framework.components.Follower;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Obstacle;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Velocity;
import framework.components.Zombie;
import framework.components.ZombieSpawner;
import framework.enums.LayerEnum;
import framework.managers.EntityManager;

public class ZombieSpawnSystem extends CoreSystem{

	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		long now = Time.getTime();
		Random r = new Random();
		for (CoreEntity spawner : em.getEntity(ZombieSpawner.class))
		{
			if (em.getEntity(Zombie.class).size() + 1 > 10)
				return;
			
			ZombieSpawner spawn = em.getComponent(spawner, ZombieSpawner.class);
			Point pos = em.getComponent(spawner, Position.class).position;
			
			boolean tooClose = false;
			for (CoreEntity hero : em.getEntity(Hero.class))
			{
				Point hpos = em.getComponent(hero, Position.class).position;
				if (hpos.dist(pos) < 500)
					tooClose = true;
			}
			if (tooClose)
				continue;
			
			if (now - spawn.delay > spawn.last)
			{
				CoreEntity zombie = new CoreEntity();
				zombie.name = "Zombie";
				zombie.components.add(new Zombie());
				zombie.components.add(new CollisionCircle(20));
				zombie.components.add(new Position(new Point(pos)));
				zombie.components.add(new Velocity(new Point(0, 0), 0.5 + r.nextDouble()));
				zombie.components.add(new Acceleration(new Point(0, 0)));
				zombie.components.add(new Health());
				zombie.components.add(new Follower(0));
				zombie.components.add(new Damage(1, 200));
				zombie.components.add(new Obstacle());
				zombie.components.add(new Collider(4));
				zombie.components.add(new Angle(0));
				zombie.components.add(new AngleSpeed(0));
				zombie.components.add(new Tex("zombie.png", new Point(40, 40)).setLayer(LayerEnum.MOVER));
				em.addEntity(zombie);
				
				spawn.last = now;
			}
		}
	}
}
