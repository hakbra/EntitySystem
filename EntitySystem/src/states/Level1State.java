package states;

import helpers.Point;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import engine.GLEngine;
import framework.Entity;
import framework.World;
import framework.components.Acceleration;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.Follower;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.Item;
import framework.components.KeyInput;
import framework.components.Light;
import framework.components.Obstacle;
import framework.components.Pathfinder;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Trigger;
import framework.components.Velocity;
import framework.components.Zombie;
import framework.systems.CameraSystem;
import framework.systems.CollisionSystem;
import framework.systems.EmitterSystem;
import framework.systems.FollowerSystem;
import framework.systems.HudRenderSystem;
import framework.systems.KeyInputSystem;
import framework.systems.LightSystem;
import framework.systems.MouseInputSystem;
import framework.systems.PathSystem;
import framework.systems.PhysicsSystem;
import framework.systems.PlayerInputSystem;
import framework.systems.SubLightRenderSystem;
import framework.systems.SurLightRenderSystem;
import framework.systems.TimerSystem;

public class Level1State {

	public static void init(World world)
	{
		//Systems
		world.addSystem(new PlayerInputSystem(world), State.LEVEL1);
		world.addSystem(new CameraSystem(world), State.LEVEL1);
		world.addSystem(new PathSystem(world), State.LEVEL1);
		world.addSystem(new FollowerSystem(world), State.LEVEL1);
		world.addSystem(new PhysicsSystem(world), State.LEVEL1);
		world.addSystem(new CollisionSystem(world), State.LEVEL1);

		world.addSystem(new SubLightRenderSystem(world), State.LEVEL1);
		world.addSystem(new LightSystem(world), State.LEVEL1);
		world.addSystem(new SurLightRenderSystem(world), State.LEVEL1);
		world.addSystem(new HudRenderSystem(world), State.LEVEL1);

		world.addSystem(new EmitterSystem(world), State.LEVEL1);
		world.addSystem(new MouseInputSystem(world), State.LEVEL1);
		world.addSystem(new KeyInputSystem(world), State.LEVEL1);
		world.addSystem(new TimerSystem(world), State.LEVEL1);

		Entity player = new Entity();
		player.name = "player1";
		player.components.add(new Hero());
		player.components.add(new Circle(20));
		player.components.add(new Position(new Point(300, 250)));
		player.components.add(new Velocity(new Point(0, 0)));
		player.components.add(new Angle(0));
		player.components.add(new AngleSpeed(0));
		player.components.add(new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_SPACE));
		player.components.add(new Gun(10, 0, 10, 100, 2, "gun1.png"));
		player.components.add(new Health());
		player.components.add(new Collider(4));
		player.components.add(new Obstacle());
		player.components.add(new Light(300));
		player.components.add(new Tex("man.png", new Point(1, 1), new Point(0, 0)));
		world.addEntityAndID(player, State.LEVEL1);

		Entity worldEntity = new Entity();
		worldEntity.name = "camera";
		worldEntity.components.add(new Position( new Point()));
		worldEntity.components.add(new Polygon( new Point(0, 0), new Point(GLEngine.WIDTH, 0)));
		worldEntity.components.add(new Pathfinder(new Point(0, 0), new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT), 10));
		world.addEntityAndID(worldEntity, State.LEVEL1);

		Entity ground = new Entity();
		ground.name = "ground";
		ground.components.add(new Position( new Point()));
		ground.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		ground.components.add(new Tex("bush.png", new Point(128, 128)));
		world.addEntityAndID(ground, State.LEVEL1);

		Entity gun = new Entity();
		gun.name = "gun";
		gun.components.add(new Position(new Point(GLEngine.WIDTH * 2 - 75, 75)));
		gun.components.add(new Circle(30));
		gun.components.add(new Item("gun"));
		gun.components.add(new Tex("gun2.png"));
		gun.components.add(new Gun(10, 10, 10, 400, 20, "gun2.png"));
		gun.components.add(new Angle(0));
		gun.components.add(new AngleSpeed(1));
		world.addEntity(gun, State.LEVEL1);

		Entity gun2 = new Entity();
		gun2.name = "gun2";
		gun2.components.add(new Position(new Point(75, GLEngine.HEIGHT - 75)));
		gun2.components.add(new Circle(30));
		gun2.components.add(new Item("gun"));
		gun2.components.add(new Tex("gun3.png"));
		gun2.components.add(new Gun(5, 35, 10, 400, 10, "gun3.png"));
		gun2.components.add(new Angle(0));
		gun2.components.add(new AngleSpeed(1));
		world.addEntity(gun2, State.LEVEL1);

		createButtons(world);
		createHealth(world);
		createWalls(world);
		createZombies(world);
	}

	private static void createHealth(World world)
	{
		Entity health = new Entity();
		health.name = "health";
		health.components.add(new Circle(15));
		health.components.add(new Position(new Point(GLEngine.WIDTH * 2 - 75	, 	GLEngine.HEIGHT - 75)));
		health.components.add(new Tex("health.png"));
		health.components.add(new Item("health", 100));
		health.components.add(new Angle(0));
		health.components.add(new AngleSpeed(1));
		world.addEntity(health, State.LEVEL1);
	}

	private static void createWalls(World world) {
		ArrayList<Point> p = new ArrayList<Point>();

		p.add(  new Point(400, 50)  );
		p.add(  new Point(150, 110)  );

		/**/
		p.add(  new Point(50, 150)  );
		p.add(  new Point(150, 410)  );

		p.add(  new Point(50, 50)  );
		p.add(  new Point(150, 335)  );

		p.add(  new Point(50, 150)  );
		p.add(  new Point(150, 160)  );
		/**/

		p.add(  new Point(400, 50)  );
		p.add(  new Point(150, 560)  );

		p.add(  new Point(400, 50)  );
		p.add(  new Point(730, 110)  );

		/**/
		p.add(  new Point(50, 150)  );
		p.add(  new Point(1080, 410)  );

		p.add(  new Point(50, 50)  );
		p.add(  new Point(1080, 335)  );

		p.add(  new Point(50, 150)  );
		p.add(  new Point(1080, 160)  );
		/**/

		p.add(  new Point(400, 50)  );
		p.add(  new Point(730, 550)  );

		p.add(  new Point(600, 50)  );
		p.add(  new Point(340, 335)  );

		p.add(  new Point(50, 300) );
		p.add(  new Point(GLEngine.WIDTH + 50, 0)  );

		p.add(  new Point(50, 300) );
		p.add(  new Point(GLEngine.WIDTH + 50, 420)  );


		p.add(  new Point(50, 300) );
		p.add(  new Point(GLEngine.WIDTH*2 - 300, 0)  );

		p.add(  new Point(50, 300) );
		p.add(  new Point(GLEngine.WIDTH*2 - 300, GLEngine.HEIGHT - 300)  );

		p.add(  new Point(150, 150) );
		p.add(  new Point(GLEngine.WIDTH + 400, 200)  );

		p.add(  new Point(150, 150) );
		p.add(  new Point(GLEngine.WIDTH + 400, 520)  );

		p.add(  new Point(150, 150) );
		p.add(  new Point(GLEngine.WIDTH + 760, 200)  );

		p.add(  new Point(150, 150) );
		p.add(  new Point(GLEngine.WIDTH + 760, 520)  );


		while (p.size() > 0)
		{
			Entity rectangle = new Entity();
			rectangle.name = "rectangle";
			if (p.size() < 9)
			{
				rectangle.components.add(Polygon.centerRectangle(p.remove(0)));
				rectangle.components.add(new Angle(45));
				rectangle.components.add(new AngleSpeed(0.2));
				rectangle.components.add(new Tex("crate.png", new Point(150, 150)));
			}
			else
			{
				rectangle.components.add(Polygon.rectangle(p.remove(0)));
				rectangle.components.add(new Tex("crate.png", new Point(50, 50)));
			}
			rectangle.components.add(new Position(p.remove(0)));
			rectangle.components.add(new Obstacle());
			world.addEntity(rectangle, State.LEVEL1);

		}

		Entity border = new Entity();
		border.name = "border";
		border.components.add(Polygon.invertedRectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		border.components.add(new Position(new Point(0, 0)));
		border.components.add(new Obstacle());
		world.addEntity(border, State.LEVEL1);

		Entity trigger = new Entity();
		trigger.name = "trigger";
		trigger.components.add(Polygon.rectangle(new Point(150, 150)));
		trigger.components.add(new Position( new Point(GLEngine.WIDTH*2-150, 0)));
		trigger.components.add(new Trigger("gun"));
		world.addEntity(trigger, State.LEVEL1);
	}

	private static void createButtons(World world)
	{

		Entity menuButton = new Entity();
		menuButton.name = "button";
		menuButton.components.add(Polygon.rectangle(new Point(100, 50)));
		menuButton.components.add(new Position(new Point(150, 650)));
		menuButton.components.add(new Button("Menu"));
		menuButton.components.add(new Tex("button.png"));
		world.addEntity(menuButton, State.LEVEL1);

		Entity exitButton2 = new Entity();
		exitButton2.name = "exitButton";
		exitButton2.components.add(Polygon.rectangle(new Point(100, 50)));
		exitButton2.components.add(new Position(new Point(25, 650)));
		exitButton2.components.add(new Button("Exit"));
		exitButton2.components.add(new Tex("button.png"));
		world.addEntity(exitButton2, State.LEVEL1);

		Entity zombieButton = new Entity();
		zombieButton.name = "zombieButton";
		zombieButton.components.add(Polygon.rectangle(new Point(100, 50)));
		zombieButton.components.add(new Position(new Point(400, 650)));
		zombieButton.components.add(new Button("Zombies"));
		zombieButton.components.add(new Tex("button.png"));
		world.addEntity(zombieButton, State.LEVEL1);

		Entity screenButton = new Entity();
		screenButton.name = "screenButton";
		screenButton.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(525, 650)));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Tex("button.png"));
		world.addEntity(screenButton, State.LEVEL1);

		Entity lightButton = new Entity();
		lightButton.name = "lightButton";
		lightButton.components.add(Polygon.rectangle(new Point(100, 50)));
		lightButton.components.add(new Position(new Point(275, 650)));
		lightButton.components.add(new Button("Lights"));
		lightButton.components.add(new Tex("button.png"));
		world.addEntity(lightButton, State.LEVEL1);

		Entity restartButton = new Entity();
		restartButton.name = "restartButton";
		restartButton.components.add(Polygon.rectangle(new Point(100, 50)));
		restartButton.components.add(new Position(new Point(650, 650)));
		restartButton.components.add(new Button("Restart"));
		restartButton.components.add(new Tex("button.png"));
		world.addEntity(restartButton, State.LEVEL1);
	}

	private static void createZombies(World world)
	{

		ArrayList<Point> spawns = new ArrayList<Point>();

		spawns.add(  new Point(50, 50));
		spawns.add(  new Point(GLEngine.WIDTH - 50, 50));
		spawns.add(  new Point(50, GLEngine.HEIGHT - 50));
		spawns.add(  new Point(GLEngine.WIDTH - 50, GLEngine.HEIGHT - 50));

		spawns.add(  new Point(GLEngine.WIDTH / 2, 50));
		spawns.add(  new Point(GLEngine.WIDTH / 2, GLEngine.HEIGHT - 50));
		spawns.add(  new Point(GLEngine.WIDTH - 50, GLEngine.HEIGHT / 2));
		spawns.add(  new Point(50, GLEngine.HEIGHT / 2));

		spawns.add(  new Point(GLEngine.WIDTH + 560, 500));
		spawns.add(  new Point(GLEngine.WIDTH + 560, 225));
		spawns.add(  new Point(GLEngine.WIDTH + 700, 360));

		spawns.add(  new Point(GLEngine.WIDTH*2 -20, 100));
		spawns.add(  new Point(GLEngine.WIDTH*2 -20, GLEngine.HEIGHT - 100));
		
		Random r = new Random();
		for (int i = 0; i < 20; i++)
			spawns.add(new Point(r.nextInt(GLEngine.WIDTH) + GLEngine.WIDTH, r.nextInt(GLEngine.HEIGHT)));

		for (Point p : spawns)
		{
			Entity zombie = new Entity();
			zombie.name = "Zombie";
			zombie.components.add(new Zombie());
			zombie.components.add(new Circle(20));
			zombie.components.add(new Position(p));
			zombie.components.add(new Velocity(new Point(0, 0)));
			zombie.components.add(new Acceleration(new Point(0, 0)));
			zombie.components.add(new Health());
			zombie.components.add(new Follower());
			zombie.components.add(new Damage(1, 200));
			zombie.components.add(new Obstacle());
			zombie.components.add(new Collider(4));
			zombie.components.add(new Angle(0));
			zombie.components.add(new AngleSpeed(0));
			zombie.components.add(new Tex("zombie.png"));
			world.addEntity(zombie, State.LEVEL1);
		}

	}
}
