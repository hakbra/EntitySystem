package framework.systems;

import helpers.Point;
import helpers.Time;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import states.CutsceneState;
import states.Level2State;
import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.Layer;
import framework.State;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.EmitterOnImpact;
import framework.components.Follower;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Item;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.components.Trigger;
import framework.components.Velocity;
import framework.components.Zombie;


public class CollisionSystem extends CoreSystem {

	private class Collision
	{
		public Entity a;
		public Entity b;
		public Point poi;
		public boolean inside;

		public Collision(Entity e1, Entity e2, Point p, boolean i)
		{
			this.a = e1;
			this.b = e2;
			this.poi = p;
			this.inside = i;
		}
	}

	public CollisionSystem(World w)
	{
		super(w);
	}

	@Override
	public void run(EntityManager em)
	{
		for (Entity e1 : em.getEntity(Collider.class))
		{
			Point circle1pos = em.getComponent(e1, Position.class).position;
			Circle circle1 = em.getComponent(e1, Circle.class);

			for (Entity e2 : em.getEntityAll(Circle.class))
			{
				if (e1 == e2)
					continue;

				Circle circle2 = em.getComponent(e2, Circle.class);

				Point col = circle2.getClosest(circle1pos);
				boolean inside = circle2.isInside(circle1pos);

				if (circle1pos.dist(col) < circle1.radius || inside)
					handleCollision( em, new Collision(e1, e2, col, inside) );
			}

			for (Entity e2 : em.getEntityAll(Polygon.class))
			{
				Polygon poly = em.getComponent(e2, Polygon.class);

				Point col = poly.getClosest(circle1pos);
				boolean inside = poly.isInside(circle1pos);

				if (circle1pos.dist(col) < circle1.radius || inside)
					handleCollision( em, new Collision(e1, e2, col, inside) );
			}
		}
	}

	private void handleCollision(EntityManager em, Collision c)
	{
		if (em.hasComponent(c.a, Collider.class) && em.hasComponent(c.b, Obstacle.class))
		{
			Point posA = em.getComponent(c.a, Position.class).position;
			Point velA = em.getComponent(c.a, Velocity.class).velocity;
			Circle circle = em.getComponent(c.a, Circle.class);

			//if (c.inside)
			//	c.poi.isub(velA);
			double dist = posA.dist(c.poi) - circle.radius;
			if (c.inside)
				dist += 2*circle.radius;
			Point mov = posA.sub(c.poi).norm(dist);

			if (em.hasComponent(c.b, Collider.class))
			{
				Point posB = em.getComponent(c.b, Position.class).position;
				int levelA = em.getComponent(c.a, Collider.class).level;
				int levelB = em.getComponent(c.b, Collider.class).level;

				if (levelA > levelB)
					posB.iadd(mov);
				else if (levelA < levelB)
					posA.isub(mov);
				else
				{
					posB.iadd(mov.div(2));
					posA.isub(mov.div(2));
				}
			}
			else
				posA.isub(mov);
		}

		if (em.hasComponent(c.b, Obstacle.class))
		{
			if (em.hasComponent(c.a, DestroyOnImpact.class))
				em.removeEntity(c.a);

			if (em.hasComponent(c.a, EmitterOnImpact.class))
			{
				Entity emitter = new Entity();
				emitter.name = "emitter";
				em.addComponent(emitter, new Position(c.poi));
				em.addComponent(emitter, new Timer(0));
				em.addComponent(emitter, new Emitter());
			}
		}
		else if (em.hasComponent(c.b, Item.class))
		{
			Item item = em.getComponent(c.b, Item.class);
			if (item.type == "health" &&
					em.hasComponents(c.a, Health.class, Hero.class))
			{
				Health health = em.getComponent(c.a, Health.class);
				if (health.current < health.max)
				{
					health.current += item.value;
					if (health.current > health.max)
						health.current = health.max;
					em.removeEntity(c.b);
				}

			}
			if (item.type == "gun" &&
					Keyboard.isKeyDown(Keyboard.KEY_E) &&
					em.hasComponents(c.a, Gun.class, Hero.class) &&
					!em.hasComponent(c.b, Timer.class))
			{
				Gun oldGun = em.getComponent(c.a, Gun.class);
				em.removeComponent(c.a, oldGun);

				Gun newGun = em.getComponent(c.b, Gun.class);
				em.addComponent(c.a, newGun);

				em.addComponent(c.b, oldGun);
				em.addComponent(c.b, new Timer(500, "selfDestruct"));
				em.getComponent(c.b, Tex.class).texture = oldGun.tex;
			}
			if (item.type == "exit1" &&
					em.hasComponents(c.a, Gun.class, Hero.class))
			{
				int i = 0;
				for (Entity h : em.getEntity(Hero.class))
				{
					em.getComponent(h, Position.class).position.set(GLEngine.WIDTH - 50, GLEngine.HEIGHT / 2 - 40 + 80*i);
					em.getComponent(h, Velocity.class).velocity.set(-4, 0);
					em.getComponent(h, Angle.class).angle = 180.0;
					em.getComponent(h, AngleSpeed.class).speed = 0.0;
					world.addEntity(h, State.CUTSCENE);
					i++;
				}
				world.clear(world.state);
				CutsceneState.init(world);
				world.state = State.CUTSCENE;
			}
			if (item.type == "exit2" &&
					em.hasComponents(c.a, Gun.class, Hero.class))
			{
				int i = 0;
				for (Entity h : em.getEntity(Hero.class))
				{
					em.getComponent(h, Position.class).position.set(GLEngine.WIDTH - 50, GLEngine.HEIGHT / 2 - 40 + 80*i);
					em.getComponent(h, Velocity.class).velocity.set(0, 0);
					em.getComponent(h, Angle.class).angle = 180.0;
					em.getComponent(h, AngleSpeed.class).speed = 0.0;
					world.addEntity(h, State.LEVEL2);
					i++;
				}
				world.clear(world.state);
				Level2State.init(world);
				world.state = State.LEVEL2;
			}
		}
		else if (em.hasComponent(c.b, Trigger.class) && em.hasComponent(c.a, Hero.class))
		{
			Trigger trigger = em.getComponent(c.b, Trigger.class);
			if (trigger.type == "gun")
			{
				Random r = new Random();
				for (int i = 0; i < 20; i++)
				{
					Entity zombie = new Entity();
					zombie.name = "Zombie";
					zombie.layer = Layer.MOVER;
					em.addComponent(zombie, new Zombie());
					em.addComponent(zombie, new Circle(20));
					em.addComponent(zombie, new Position(new Point(r.nextInt(GLEngine.WIDTH), r.nextInt(GLEngine.HEIGHT))));
					em.addComponent(zombie, new Velocity(new Point(0, 0)));
					em.addComponent(zombie, new Health());
					em.addComponent(zombie, new Follower());
					em.addComponent(zombie, new Damage(1, 200));
					em.addComponent(zombie, new Obstacle());
					em.addComponent(zombie, new Collider(4));
					em.addComponent(zombie, new Angle(0));
					em.addComponent(zombie, new AngleSpeed(0));
					em.addComponent(zombie, new Tex("zombie.png"));
				}

				for (int j = 0; j < 10; j++)
				{
					Entity zombie = new Entity();
					zombie.name = "Zombie" + j;
					zombie.layer = Layer.MOVER;
					em.addComponent(zombie, new Zombie());
					em.addComponent(zombie, new Circle(20));
					em.addComponent(zombie, new Position(new Point(GLEngine.WIDTH + 200 + j * 40, GLEngine.HEIGHT / 2)));
					em.addComponent(zombie, new Velocity(new Point(0, 0)));
					em.addComponent(zombie, new Health());
					em.addComponent(zombie, new Follower(0));
					em.addComponent(zombie, new Damage(1, 200));
					em.addComponent(zombie, new Obstacle());
					em.addComponent(zombie, new Collider(4));
					em.addComponent(zombie, new Angle(0));
					em.addComponent(zombie, new AngleSpeed(0));
					em.addComponent(zombie, new Tex("zombie.png"));

					if (j % 2 == 0)
						continue;

					Entity light = new Entity();
					light.name = "light" + j;
					em.addComponent(light, new Position(new Point(GLEngine.WIDTH + 200 + j * 80, GLEngine.HEIGHT / 2)));
					em.addComponent(light, new Timer(2000 - 200*j, "light200"));
				}

				em.removeEntity(c.b);

				Entity exit = new Entity();
				exit.name = "exit1";
				exit.layer = Layer.ITEM;
				em.addComponent(exit, new Position(new Point(250, 200)));
				em.addComponent(exit, Polygon.centerRectangle(new Point(50, 50)));
				em.addComponent(exit, new Item("exit1"));
				em.addComponent(exit, new Tex("exit.png"));
				em.addComponent(exit, new Angle(180));
			}	
		}

		if (em.hasComponent(c.a, Zombie.class) && em.hasComponent(c.b, Zombie.class))
			return;

		if (em.hasComponent(c.a, Damage.class) && em.hasComponent(c.b, Health.class))
		{
			Damage dam = em.getComponent(c.a, Damage.class);
			Health health = em.getComponent(c.b, Health.class);

			if (c.b == dam.parent)
				return;

			if (!dam.canDamage())
				return;

			dam.time = Time.getTime();

			health.current -= dam.amount;
			if (health.current <= 0)
				em.removeEntity(c.b);

			dam.time = Time.getTime();
		}
	}
}
