package states;

import helpers.Point;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import engine.GLEngine;
import framework.CoreEntity;
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
import framework.enums.LayerEnum;
import framework.enums.StateEnum;
import framework.systems.CameraSystem;
import framework.systems.CollisionSystem;
import framework.systems.DamageSystem;
import framework.systems.EmitterSystem;
import framework.systems.FollowerSystem;
import framework.systems.IntersectionSystem;
import framework.systems.LightSystem;
import framework.systems.PathSystem;
import framework.systems.PhysicsSystem;
import framework.systems.TimerSystem;
import framework.systems.TriggerSystem;
import framework.systems.input.KeyInputSystem;
import framework.systems.input.MouseInputSystem;
import framework.systems.input.PlayerInputSystem;
import framework.systems.render.RenderSystem;

public class Level1State {

	public static void init(World world)
	{
		world.clearState(StateEnum.LEVEL1);
		
		//Systems

		world.addSystem(new CameraSystem(world), StateEnum.LEVEL1);
		world.addSystem(new LightSystem(world), StateEnum.LEVEL1);
		world.addSystem(new RenderSystem(world), StateEnum.LEVEL1);
		
		world.addSystem(new PlayerInputSystem(world), StateEnum.LEVEL1);
		world.addSystem(new PathSystem(world), StateEnum.LEVEL1);
		world.addSystem(new FollowerSystem(world), StateEnum.LEVEL1);
		world.addSystem(new PhysicsSystem(world), StateEnum.LEVEL1);
		world.addSystem(new IntersectionSystem(world), StateEnum.LEVEL1);
		
		world.addSystem(new CollisionSystem(world), StateEnum.LEVEL1);
		world.addSystem(new TriggerSystem(world), StateEnum.LEVEL1);
		world.addSystem(new DamageSystem(world), StateEnum.LEVEL1);

		world.addSystem(new EmitterSystem(world), StateEnum.LEVEL1);
		world.addSystem(new MouseInputSystem(world), StateEnum.LEVEL1);
		world.addSystem(new KeyInputSystem(world), StateEnum.LEVEL1);
		world.addSystem(new TimerSystem(world), StateEnum.LEVEL1);

		CoreEntity player = new CoreEntity();
		player.name = "Player 1";
		player.layer = LayerEnum.MOVER;
		player.components.add(new Hero());
		player.components.add(new Circle(20));
		player.components.add(new Position(new Point(400, 250)));
		player.components.add(new Velocity(new Point(0, 0)));
		player.components.add(new Angle(0));
		player.components.add(new AngleSpeed(0));
		player.components.add(new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_SPACE, Keyboard.KEY_E));
		player.components.add(new Gun(5, 0, 10, 100, 2, "gun1.png"));
		player.components.add(new Health());
		player.components.add(new Collider(4));
		player.components.add(new Obstacle());
		player.components.add(new Light(300));
		player.components.add(new Tex("man.png", new Point(1, 1), new Point(0, 0)));
		world.addEntity(player, StateEnum.LEVEL1);
		world.registerID(player, StateEnum.LEVEL1);

		CoreEntity camera = new CoreEntity();
		camera.name = "camera";
		camera.components.add(new Position( new Point()));
		camera.components.add(new Polygon( new Point(0, 0), new Point(GLEngine.WIDTH, 0)));
		world.addEntity(camera, StateEnum.LEVEL1);
		world.registerID(camera, StateEnum.LEVEL1);
		
		CoreEntity path = new CoreEntity();
		path.name = "path";
		path.components.add(new Pathfinder(new Point(0, 0), new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT), 20));
		world.addEntity(path, StateEnum.LEVEL1);
		world.registerID(path, StateEnum.LEVEL1);

		CoreEntity ground = new CoreEntity();
		ground.name = "ground";
		ground.layer = LayerEnum.GROUND;
		ground.components.add(new Position( new Point()));
		ground.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		ground.components.add(new Tex("bush.png", new Point(128, 128)));
		world.addEntity(ground, StateEnum.LEVEL1);
		world.registerID(ground, StateEnum.LEVEL1);
		
		CoreEntity light = new CoreEntity();
		light.name = "light";
		light.layer = LayerEnum.LIGHT;
		light.components.add(new Position( new Point(), true));
		light.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH, GLEngine.HEIGHT)));
		light.components.add(new Tex("lightTex"));
		world.addEntity(light, StateEnum.LEVEL1);
		
		CoreEntity exit = new CoreEntity();
		exit.name = "exit1";
		exit.layer = LayerEnum.ITEM;
		exit.components.add(new Position(new Point(GLEngine.WIDTH * 2 - 75, GLEngine.HEIGHT / 2)));
		exit.components.add(Polygon.centerRectangle(new Point(50, 50)));
		exit.components.add(new Trigger("exit1"));
		exit.components.add(new Tex("exit.png"));
		exit.components.add(new Angle(180));
		world.addEntity(exit, StateEnum.LEVEL1);

		createButtons(world);
		createItems(world);
		createWalls(world);
		createZombies(world);
	}

	private static void createItems(World world)
	{
		CoreEntity health = new CoreEntity();
		health.name = "health";
		health.layer = LayerEnum.ITEM;
		health.components.add(new Circle(15));
		health.components.add(new Position(new Point(GLEngine.WIDTH * 2 - 75	, 	GLEngine.HEIGHT - 75)));
		health.components.add(new Tex("health.png"));
		health.components.add(new Trigger("health"));
		health.components.add(new Angle(0));
		health.components.add(new AngleSpeed(1));
		world.addEntity(health, StateEnum.LEVEL1);

		CoreEntity gun = new CoreEntity();
		gun.name = "gun";
		gun.layer = LayerEnum.ITEM;
		gun.components.add(new Position(new Point(GLEngine.WIDTH * 2 - 75, 75)));
		gun.components.add(new Circle(30));
		gun.components.add(new Trigger("gun"));
		gun.components.add(new Tex("gun2.png"));
		gun.components.add(new Gun(5, 10, 10, 400, 20, "gun2.png"));
		gun.components.add(new Angle(0));
		gun.components.add(new AngleSpeed(1));
		world.addEntity(gun, StateEnum.LEVEL1);

		CoreEntity gun2 = new CoreEntity();
		gun2.name = "gun2";
		gun2.layer = LayerEnum.ITEM;
		gun2.components.add(new Position(new Point(75, GLEngine.HEIGHT - 75)));
		gun2.components.add(new Circle(30));
		gun2.components.add(new Trigger("gun"));
		gun2.components.add(new Tex("gun3.png"));
		gun2.components.add(new Gun(5, 35, 10, 400, 10, "gun3.png"));
		gun2.components.add(new Angle(0));
		gun2.components.add(new AngleSpeed(1));
		world.addEntity(gun2, StateEnum.LEVEL1);
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
			CoreEntity rectangle = new CoreEntity();
			rectangle.name = "rectangle";
			rectangle.layer = LayerEnum.OBSTACLE;
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
			world.addEntity(rectangle, StateEnum.LEVEL1);

		}

		CoreEntity border = new CoreEntity();
		border.name = "border";
		border.components.add(Polygon.invertedRectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		border.components.add(new Position(new Point(0, 0)));
		border.components.add(new Obstacle());
		world.addEntity(border, StateEnum.LEVEL1);
	}

	private static void createButtons(World world)
	{
		CoreEntity exitButton2 = new CoreEntity();
		exitButton2.name = "exitButton";
		exitButton2.layer = LayerEnum.HUD;
		exitButton2.components.add(Polygon.rectangle(new Point(100, 50)));
		exitButton2.components.add(new Position(new Point(25, 650), true));
		exitButton2.components.add(new Button("Exit"));
		exitButton2.components.add(new Tex("button.png"));
		world.addEntity(exitButton2, StateEnum.LEVEL1);

		CoreEntity menuButton = new CoreEntity();
		menuButton.name = "button";
		menuButton.layer = LayerEnum.HUD;
		menuButton.components.add(Polygon.rectangle(new Point(100, 50)));
		menuButton.components.add(new Position(new Point(150, 650), true));
		menuButton.components.add(new Button("Menu"));
		menuButton.components.add(new Tex("button.png"));
		world.addEntity(menuButton, StateEnum.LEVEL1);


		CoreEntity zombieButton = new CoreEntity();
		zombieButton.name = "zombieButton";
		zombieButton.layer = LayerEnum.HUD;
		zombieButton.components.add(Polygon.rectangle(new Point(100, 50)));
		zombieButton.components.add(new Position(new Point(275, 650), true));
		zombieButton.components.add(new Button("Zombies"));
		zombieButton.components.add(new Tex("button.png"));
		world.addEntity(zombieButton, StateEnum.LEVEL1);

		CoreEntity screenButton = new CoreEntity();
		screenButton.name = "screenButton";
		screenButton.layer = LayerEnum.HUD;
		screenButton.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(400, 650), true));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Tex("button.png"));
		world.addEntity(screenButton, StateEnum.LEVEL1);

		CoreEntity restartButton = new CoreEntity();
		restartButton.name = "restartButton";
		restartButton.layer = LayerEnum.HUD;
		restartButton.components.add(Polygon.rectangle(new Point(100, 50)));
		restartButton.components.add(new Position(new Point(525, 650), true));
		restartButton.components.add(new Button("Restart"));
		restartButton.components.add(new Tex("button.png"));
		world.addEntity(restartButton, StateEnum.LEVEL1);
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
			CoreEntity zombie = new CoreEntity();
			zombie.name = "Zombie";
			zombie.layer = LayerEnum.MOVER;
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
			world.addEntity(zombie, StateEnum.LEVEL1);
		}

	}
}
