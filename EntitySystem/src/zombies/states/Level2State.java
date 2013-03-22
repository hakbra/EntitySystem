package zombies.states;


import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import zombies.components.Acceleration;
import zombies.components.Angle;
import zombies.components.AngleSpeed;
import zombies.components.Button;
import zombies.components.Collider;
import zombies.components.CollisionCircle;
import zombies.components.CollisionPolygon;
import zombies.components.Damage;
import zombies.components.Follower;
import zombies.components.GriffPart;
import zombies.components.Gun;
import zombies.components.Health;
import zombies.components.Hero;
import zombies.components.KeyInput;
import zombies.components.Light;
import zombies.components.Obstacle;
import zombies.components.Position;
import zombies.components.RenderPolygon;
import zombies.components.Tex;
import zombies.components.Text;
import zombies.components.Trigger;
import zombies.components.Velocity;
import zombies.components.Zombie;
import zombies.components.ZombieSpawner;
import zombies.systems.CameraSystem;
import zombies.systems.CollisionSystem;
import zombies.systems.DamageSystem;
import zombies.systems.EmitterSystem;
import zombies.systems.FollowerSystem;
import zombies.systems.IntersectionSystem;
import zombies.systems.LightSystem;
import zombies.systems.PhysicsSystem;
import zombies.systems.TimerSystem;
import zombies.systems.TriggerSystem;
import zombies.systems.ZombieSpawnSystem;
import zombies.systems.input.KeyInputSystem;
import zombies.systems.input.MouseInputSystem;
import zombies.systems.input.PlayerInputSystem;
import zombies.systems.render.RenderSystem;
import zombies.systems.render.StatsSystem;
import framework.CoreEntity;
import framework.DynEnum;
import framework.World;
import framework.engine.GLEngine;
import framework.utils.Color;
import framework.utils.Point;

public class Level2State {

	public static int MAPWIDTH = 1280;
	public static int MAPHEIGHT = 720*2;

	public static void init(World world)
	{
		world.mapdim.x = MAPWIDTH;
		world.mapdim.y = MAPHEIGHT;

		//Systems
		world.addSystem(new CameraSystem());
		world.addSystem(new LightSystem());
		world.addSystem(new RenderSystem());
		world.addSystem(new StatsSystem());

		world.addSystem(new PlayerInputSystem());
		world.addSystem(new FollowerSystem());
		world.addSystem(new PhysicsSystem());
		world.addSystem(new IntersectionSystem());

		world.addSystem(new CollisionSystem());
		world.addSystem(new TriggerSystem());
		world.addSystem(new DamageSystem());

		world.addSystem(new EmitterSystem());
		world.addSystem(new MouseInputSystem());
		world.addSystem(new KeyInputSystem());
		world.addSystem(new ZombieSpawnSystem());
		world.addSystem(new TimerSystem());

		CoreEntity player = new CoreEntity();
		player.name = "Player 1";

		player.components.add(new Hero());
		player.components.add(new CollisionCircle(20));
		player.components.add(new Position(new Point(50, 360)));
		player.components.add(new Velocity(new Point(0, 0), 4));
		player.components.add(new Angle(0));
		player.components.add(new AngleSpeed(0));
		player.components.add(new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_SPACE, Keyboard.KEY_E));
		player.components.add(new Gun(5, 0, 10, 100, 2, "gun1.png"));
		player.components.add(new Health());
		player.components.add(new Collider(4));
		player.components.add(new Obstacle());
		player.components.add(new Light(300));
		player.components.add(new Tex("man.png", new Point(40, 40)).setLayer(DynEnum.at("layer").get("mover")));
		world.addEntity(player);
		world.registerID(player);

		CoreEntity ground = new CoreEntity();
		ground.name = "ground";
		ground.components.add(new Position( new Point(MAPWIDTH / 2, MAPHEIGHT / 2)));
		ground.components.add(CollisionPolygon.centerRectangle(new Point(MAPWIDTH, MAPHEIGHT)));
		ground.components.add(new Tex("bush.png", new Point(MAPWIDTH, MAPHEIGHT)).setScale(new Point(30, 19)).setLayer(DynEnum.at("layer").get("ground")));
		world.addEntity(ground);
		world.registerID(ground);

		CoreEntity light = new CoreEntity();
		light.name = "light";
		light.components.add(new Position( new Point(MAPWIDTH / 2, MAPHEIGHT / 2)));
		light.components.add(CollisionPolygon.centerRectangle(new Point(MAPWIDTH, MAPHEIGHT)));
		light.components.add(new Tex("lightTex", new Point(MAPWIDTH, MAPHEIGHT)).setLayer(DynEnum.at("layer").get("light")));
		world.addEntity(light);

		/*
		createItems(world);
		createZombies(world);
		 */
		createWalls(world);
		createButtons(world);
		createSpawns(world);
	}

	private static void createSpawns(World world) {


		ArrayList<Point> spawns = new ArrayList<Point>();

		spawns.add(  new Point(MAPWIDTH * 0.20, 	MAPHEIGHT - 75) );
		spawns.add(  new Point(MAPWIDTH * 0.40, 	MAPHEIGHT - 75) );
		spawns.add(  new Point(MAPWIDTH * 0.60, 	MAPHEIGHT - 75) );
		spawns.add(  new Point(MAPWIDTH * 0.80, 	MAPHEIGHT - 75) );
		spawns.add(  new Point(MAPWIDTH * 0.20, 	75) );
		spawns.add(  new Point(MAPWIDTH * 0.40, 	75) );
		spawns.add(  new Point(MAPWIDTH * 0.60, 	75) );
		spawns.add(  new Point(MAPWIDTH * 0.80, 	75) );

		for (Point p : spawns)
		{
			CoreEntity spawn = new CoreEntity();
			spawn.name = "spawn";
			spawn.components.add(new Position(p));
			spawn.components.add(new ZombieSpawner(5000));
			world.addEntity(spawn);
		}
	}

	private static void createItems(World world)
	{
		CoreEntity health = new CoreEntity();
		health.name = "health";

		health.components.add(new CollisionCircle(15));
		health.components.add(new Position(new Point(MAPWIDTH * 0.75	, 	MAPHEIGHT - 75)));
		health.components.add(new Tex("health.png", new Point(30, 30)).setLayer(DynEnum.at("layer").get("item")));
		health.components.add(new Trigger("health"));
		health.components.add(new Angle(0));
		health.components.add(new AngleSpeed(1));
		world.addEntity(health);

		CoreEntity gun = new CoreEntity();
		gun.name = "gun";

		gun.components.add(new Position(new Point(MAPWIDTH * 0.75, 75)));
		gun.components.add(new CollisionCircle(30));
		gun.components.add(new Trigger("gun"));
		gun.components.add(new Tex("gun2.png", new Point(60, 60)).setLayer(DynEnum.at("layer").get("item")));
		gun.components.add(new Gun(5, 10, 10, 400, 20, "gun2.png"));
		gun.components.add(new Angle(0));
		gun.components.add(new AngleSpeed(1));
		world.addEntity(gun);

		CoreEntity gun2 = new CoreEntity();
		gun2.name = "gun2";

		gun2.components.add(new Position(new Point(1050, 200)));
		gun2.components.add(new CollisionCircle(30));
		gun2.components.add(new Trigger("gun"));
		gun2.components.add(new Tex("gun3.png", new Point(60, 60)).setLayer(DynEnum.at("layer").get("item")));
		gun2.components.add(new Gun(5, 5, 10, 0, 50, "gun3.png"));
		gun2.components.add(new Angle(0));
		gun2.components.add(new AngleSpeed(1));
		world.addEntity(gun2);

		CoreEntity griffPart = new CoreEntity();
		griffPart.name = "griffPart";

		griffPart.components.add(new Position(new Point(MAPWIDTH - 150, 75)));
		griffPart.components.add(new CollisionCircle(30));
		griffPart.components.add(new Trigger("griff"));
		griffPart.components.add(new Tex("part.png", new Point(60, 60)).setLayer(DynEnum.at("layer").get("item")));
		griffPart.components.add(new GriffPart("Lion Head"));
		griffPart.components.add(new Angle(0));
		griffPart.components.add(new AngleSpeed(1));
		world.addEntity(griffPart);
	}

	private static void createWalls(World world) {
		CoreEntity rectangle = new CoreEntity();
		rectangle.name = "rectangle";

		Point dim = new Point(400, 50);
		rectangle.components.add(CollisionPolygon.centerRectangle(dim));
		rectangle.components.add(new Angle(45));
		rectangle.components.add(new AngleSpeed(0.2));
		rectangle.components.add(new Tex("crate.png", dim).setScale(new Point(50, 50)).setLayer(DynEnum.at("layer").get("obstacle")));
		rectangle.components.add(new Position(new Point(MAPWIDTH / 2, MAPHEIGHT / 2)));
		rectangle.components.add(new Obstacle());
		world.addEntity(rectangle);


		CoreEntity border = new CoreEntity();
		border.name = "border";
		border.components.add(CollisionPolygon.centerRectangle(new Point(MAPWIDTH, MAPHEIGHT)).setInverted());
		border.components.add(new Position(new Point(MAPWIDTH / 2, MAPHEIGHT / 2)));
		border.components.add(new Obstacle());
		border.components.add(RenderPolygon.centerRectangle(new Point(MAPWIDTH, MAPHEIGHT), new Color(0.3, 0.3, 0.3)).wireframe().setLayer(DynEnum.at("layer").get("obstacle")));
		world.addEntity(border);
	}

	private static void createButtons(World world)
	{
		CoreEntity exitButton2 = new CoreEntity();
		exitButton2.name = "exitButton";

		exitButton2.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		exitButton2.components.add(new Position(new Point(75, GLEngine.HEIGHT - 50), true));
		exitButton2.components.add(new Button("Exit"));
		exitButton2.components.add(new Text("Exit").setLayer(DynEnum.at("layer").get("text")));
		exitButton2.components.add(new Tex("button.png", new Point(100, 50)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(exitButton2);

		CoreEntity menuButton = new CoreEntity();
		menuButton.name = "button";

		menuButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		menuButton.components.add(new Position(new Point(200, GLEngine.HEIGHT - 50), true));
		menuButton.components.add(new Button("Menu"));
		menuButton.components.add(new Text("Menu").setLayer(DynEnum.at("layer").get("text")));
		menuButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(menuButton);

		CoreEntity screenButton = new CoreEntity();
		screenButton.name = "screenButton";

		screenButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(325, GLEngine.HEIGHT - 50), true));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Text("Screen").setLayer(DynEnum.at("layer").get("text")));
		screenButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(screenButton);

		CoreEntity restartButton = new CoreEntity();
		restartButton.name = "restartButton";
		restartButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		restartButton.components.add(new Position(new Point(450, GLEngine.HEIGHT - 50), true));
		restartButton.components.add(new Button("Restart"));
		restartButton.components.add(new Text("Restart").setLayer(DynEnum.at("layer").get("text")));
		restartButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(restartButton);
		
		CoreEntity pauseButton = new CoreEntity();
		pauseButton.name = "pauseButton";
		pauseButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		pauseButton.components.add(new Position(new Point(575, GLEngine.HEIGHT - 50), true));
		pauseButton.components.add(new Button("Pause"));
		pauseButton.components.add(new Text("Pause").setLayer(DynEnum.at("layer").get("text")));
		pauseButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(pauseButton);

		CoreEntity lightButton = new CoreEntity();
		lightButton.name = "lightButton";
		lightButton.components.add(CollisionPolygon.centerRectangle(new Point(40, 40)));
		lightButton.components.add(new Position(new Point(30, 30), true));
		lightButton.components.add(new Button("Light"));
		lightButton.components.add(new Text("O").setLayer(DynEnum.at("layer").get("text")));
		lightButton.components.add(new Tex("button.png", new Point(40, 40)).setLayer(DynEnum.at("layer").get("hud")));
		world.addEntity(lightButton);
	}

	private static void createZombies(World world)
	{

		ArrayList<Point> spawns = new ArrayList<Point>();

		spawns.add(  new Point(50, 50));
		spawns.add(  new Point(MAPWIDTH / 2 - 50, 50));
		spawns.add(  new Point(50, MAPHEIGHT - 50));
		spawns.add(  new Point(MAPWIDTH / 2 - 50, MAPHEIGHT - 50));

		spawns.add(  new Point(MAPWIDTH / 4, 50));
		spawns.add(  new Point(MAPWIDTH / 4, MAPHEIGHT - 50));
		spawns.add(  new Point(MAPWIDTH / 2 - 50, MAPHEIGHT / 2));

		spawns.add(  new Point(MAPWIDTH / 2 + 560, 500));
		spawns.add(  new Point(MAPWIDTH / 2 + 560, 225));
		spawns.add(  new Point(MAPWIDTH / 2 + 700, 360));

		spawns.add(  new Point(MAPWIDTH -20, 100));
		spawns.add(  new Point(MAPWIDTH -20, MAPHEIGHT - 100));

		Random r = new Random();
		for (int i = 0; i < 20; i++)
			spawns.add(new Point(r.nextInt(MAPWIDTH/2) + MAPWIDTH/2, r.nextInt(MAPHEIGHT)));

		for (Point p : spawns)
		{
			CoreEntity zombie = new CoreEntity();
			zombie.name = "Zombie";

			zombie.components.add(new Zombie());
			zombie.components.add(new CollisionCircle(20));
			zombie.components.add(new Position(p));
			zombie.components.add(new Velocity(new Point(0, 0), 1));
			zombie.components.add(new Acceleration(new Point(0, 0)));
			zombie.components.add(new Health());
			zombie.components.add(new Follower());
			zombie.components.add(new Damage(1, 200));
			zombie.components.add(new Obstacle());
			zombie.components.add(new Collider(4));
			zombie.components.add(new Angle(0));
			zombie.components.add(new AngleSpeed(0));
			zombie.components.add(new Tex("zombie.png", new Point(40, 40)).setLayer(DynEnum.at("layer").get("mover")));
			world.addEntity(zombie);
		}

	}
}
