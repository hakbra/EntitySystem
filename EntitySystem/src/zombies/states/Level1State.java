package zombies.states;

import helpers.Color;
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
import framework.components.Collider;
import framework.components.CollisionCircle;
import framework.components.CollisionPolygon;
import framework.components.Damage;
import framework.components.Follower;
import framework.components.GriffPart;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.KeyInput;
import framework.components.Light;
import framework.components.Obstacle;
import framework.components.Pathfinder;
import framework.components.Position;
import framework.components.RenderPolygon;
import framework.components.Tex;
import framework.components.Text;
import framework.components.Trigger;
import framework.components.Velocity;
import framework.components.Zombie;
import framework.components.ZombieSpawner;
import framework.enums.EventEnum;
import framework.enums.LayerEnum;
import framework.enums.StateEnum;
import framework.managers.DataManager;
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
import framework.systems.ZombieSpawnSystem;
import framework.systems.input.KeyInputSystem;
import framework.systems.input.MouseInputSystem;
import framework.systems.input.PlayerInputSystem;
import framework.systems.render.RenderSystem;
import framework.systems.render.StatsSystem;

public class Level1State {
	
	public static int MAPWIDTH = 1280*2;
	public static int MAPHEIGHT = 720;

	public static void init(World world)
	{
		DataManager dm = world.getDataManager();
		dm.mapwidth = MAPWIDTH;
		dm.mapheight = MAPHEIGHT;

		//Systems

		world.addSystem(new CameraSystem());
		world.addSystem(new LightSystem());
		
		world.addSystem(new RenderSystem());
		world.addSystem(new StatsSystem()).subscribe(EventEnum.KILL);
		
		world.addSystem(new PlayerInputSystem());
		world.addSystem(new PathSystem());
		world.addSystem(new FollowerSystem());
		world.addSystem(new PhysicsSystem());
		world.addSystem(new IntersectionSystem());
		
		world.addSystem(new CollisionSystem()).subscribe(EventEnum.COLLISION);
		world.addSystem(new TriggerSystem()).subscribe(EventEnum.TRIGGER);
		world.addSystem(new DamageSystem()).subscribe(EventEnum.DAMAGE);
		
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
		player.components.add(new Tex("man.png", new Point(40, 40)).setLayer(LayerEnum.MOVER));
		world.addEntity(player);
		world.registerID(player);

		CoreEntity camera = new CoreEntity();
		camera.name = "camera";
		camera.components.add(new Position( new Point()));
		camera.components.add(CollisionPolygon.rectangle( new Point(GLEngine.WIDTH, GLEngine.HEIGHT)));
		world.addEntity(camera);
		world.registerID(camera);

		CoreEntity path = new CoreEntity();
		path.name = "path";
		path.components.add(new Pathfinder(new Point(0, 0), new Point(MAPWIDTH, MAPHEIGHT), 20));
		world.addEntity(path);
		world.registerID(path);

		CoreEntity ground = new CoreEntity();
		ground.name = "ground";
		ground.components.add(new Position( new Point(MAPWIDTH / 2, MAPHEIGHT / 2)));
		ground.components.add(CollisionPolygon.centerRectangle(new Point(MAPWIDTH, MAPHEIGHT)));
		ground.components.add(new Tex("bush.png", new Point(MAPWIDTH, MAPHEIGHT)).setScale(new Point(30, 19)).setLayer(LayerEnum.GROUND));
		world.addEntity(ground);
		world.registerID(ground);

		CoreEntity light = new CoreEntity();
		light.name = "light";
		light.components.add(new Position( new Point(MAPWIDTH / 2, MAPHEIGHT / 2)));
		light.components.add(CollisionPolygon.centerRectangle(new Point(MAPWIDTH, MAPHEIGHT)));
		light.components.add(new Tex("lightTex", new Point(MAPWIDTH, MAPHEIGHT)).setLayer(LayerEnum.LIGHT));
		world.addEntity(light);

		CoreEntity exit = new CoreEntity();
		exit.name = "exit1";
		exit.components.add(new Position(new Point(250, 200)));
		exit.components.add(CollisionPolygon.centerRectangle(new Point(50, 50)));
		exit.components.add(new Trigger("exit1"));
		exit.components.add(new Tex("exit.png", new Point(50, 50)).setLayer(LayerEnum.ITEM));
		exit.components.add(new Angle(0));
		world.addEntity(exit);

		createButtons(world);
		createItems(world);
		createWalls(world);
		createZombies(world);
		createSpawns(world);
	}

	private static void createSpawns(World world) {


		ArrayList<Point> spawns = new ArrayList<Point>();

		spawns.add(  new Point(1050, 	500) );
		spawns.add(  new Point(75, 	75) );
		spawns.add(  new Point(MAPWIDTH * 0.75, 	75) );
		spawns.add(  new Point(MAPWIDTH * 0.75, 	MAPHEIGHT - 75) );

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
		health.components.add(new Tex("health.png", new Point(30, 30)).setLayer(LayerEnum.ITEM));
		health.components.add(new Trigger("health"));
		health.components.add(new Angle(0));
		health.components.add(new AngleSpeed(1));
		world.addEntity(health);

		CoreEntity gun = new CoreEntity();
		gun.name = "gun";
		gun.components.add(new Position(new Point(MAPWIDTH * 0.75, 75)));
		gun.components.add(new CollisionCircle(30));
		gun.components.add(new Trigger("gun"));
		gun.components.add(new Tex("gun2.png", new Point(60, 60)).setLayer(LayerEnum.ITEM));
		gun.components.add(new Gun(5, 10, 10, 400, 20, "gun2.png"));
		gun.components.add(new Angle(0));
		gun.components.add(new AngleSpeed(1));
		world.addEntity(gun);

		CoreEntity gun2 = new CoreEntity();
		gun2.name = "gun2";
		gun2.components.add(new Position(new Point(1050, 200)));
		gun2.components.add(new CollisionCircle(30));
		gun2.components.add(new Trigger("gun"));
		gun2.components.add(new Tex("gun3.png", new Point(60, 60)).setLayer(LayerEnum.ITEM));
		gun2.components.add(new Gun(15, 5, 10, 50, 4, "gun3.png"));
		gun2.components.add(new Angle(0));
		gun2.components.add(new AngleSpeed(1));
		world.addEntity(gun2);

		CoreEntity griffPart = new CoreEntity();
		griffPart.name = "griffPart";
		griffPart.components.add(new Position(new Point(MAPWIDTH - 150, 75)));
		griffPart.components.add(new CollisionCircle(30));
		griffPart.components.add(new Trigger("griff"));
		griffPart.components.add(new Tex("part.png", new Point(60, 60)).setLayer(LayerEnum.ITEM));
		griffPart.components.add(new GriffPart("Apple"));
		griffPart.components.add(new Angle(0));
		griffPart.components.add(new AngleSpeed(1));
		world.addEntity(griffPart);
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
		p.add(  new Point(1280 + 50, 0)  );

		p.add(  new Point(50, 300) );
		p.add(  new Point(1280 + 50, 420)  );


		p.add(  new Point(50, 300) );
		p.add(  new Point(1280*2 - 300, 0)  );

		p.add(  new Point(50, 300) );
		p.add(  new Point(1280*2 - 300, 720 - 300)  );

		p.add(  new Point(150, 150) );
		p.add(  new Point(1280 + 400, 200)  );

		p.add(  new Point(150, 150) );
		p.add(  new Point(1280 + 400, 520)  );

		p.add(  new Point(150, 150) );
		p.add(  new Point(1280 + 760, 200)  );

		p.add(  new Point(150, 150) );
		p.add(  new Point(1280 + 760, 520)  );


		while (p.size() > 0)
		{
			CoreEntity rectangle = new CoreEntity();
			rectangle.name = "rectangle";
			Point dim = p.remove(0);
			rectangle.components.add(CollisionPolygon.centerRectangle(dim));
			if (p.size() < 9)
			{
				rectangle.components.add(new Angle(45));
				rectangle.components.add(new AngleSpeed(0.2));
				rectangle.components.add(new Tex("crate.png", dim).setLayer(LayerEnum.OBSTACLE));
				rectangle.components.add(new Position(p.remove(0)));
			}
			else
			{
				rectangle.components.add(new Tex("crate.png", dim).setScale(new Point(50, 50)).setLayer(LayerEnum.OBSTACLE));
				rectangle.components.add(new Position(p.remove(0).add(dim.div(2))));
			}
			rectangle.components.add(new Obstacle());
			world.addEntity(rectangle);

		}

		CoreEntity border = new CoreEntity();
		border.name = "border";
		border.components.add(CollisionPolygon.centerRectangle(new Point(MAPWIDTH, MAPHEIGHT)).setInverted());
		border.components.add(new Position(new Point(MAPWIDTH / 2, MAPHEIGHT / 2)));
		border.components.add(new Obstacle());
		border.components.add(RenderPolygon.centerRectangle(new Point(MAPWIDTH, MAPHEIGHT), new Color(0.3, 0.3, 0.3)).wireframe().setLayer(LayerEnum.OBSTACLE));
		world.addEntity(border);
	}

	private static void createButtons(World world)
	{
		CoreEntity exitButton2 = new CoreEntity();
		exitButton2.name = "exitButton";
		exitButton2.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		exitButton2.components.add(new Position(new Point(75, GLEngine.HEIGHT - 50), true));
		exitButton2.components.add(new Button("Exit"));
		exitButton2.components.add(new Text("Exit").setLayer(LayerEnum.TEXT));
		exitButton2.components.add(new Tex("button.png", new Point(100, 50)).setLayer(LayerEnum.HUD));
		world.addEntity(exitButton2);

		CoreEntity menuButton = new CoreEntity();
		menuButton.name = "button";
		menuButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		menuButton.components.add(new Position(new Point(200, GLEngine.HEIGHT - 50), true));
		menuButton.components.add(new Button("Menu"));
		menuButton.components.add(new Text("Menu").setLayer(LayerEnum.TEXT));
		menuButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(LayerEnum.HUD));
		world.addEntity(menuButton);

		CoreEntity screenButton = new CoreEntity();
		screenButton.name = "screenButton";
		screenButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(325, GLEngine.HEIGHT - 50), true));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Text("Screen").setLayer(LayerEnum.TEXT));
		screenButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(LayerEnum.HUD));
		world.addEntity(screenButton);

		CoreEntity restartButton = new CoreEntity();
		restartButton.name = "restartButton";
		restartButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		restartButton.components.add(new Position(new Point(450, GLEngine.HEIGHT - 50), true));
		restartButton.components.add(new Button("Restart"));
		restartButton.components.add(new Text("Restart").setLayer(LayerEnum.TEXT));
		restartButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(LayerEnum.HUD));
		world.addEntity(restartButton);

		CoreEntity lightButton = new CoreEntity();
		lightButton.name = "lightButton";
		lightButton.components.add(CollisionPolygon.centerRectangle(new Point(40, 40)));
		lightButton.components.add(new Position(new Point(30, 30), true));
		lightButton.components.add(new Button("Light"));
		lightButton.components.add(new Text("O").setLayer(LayerEnum.TEXT));
		lightButton.components.add(new Tex("button.png", new Point(40, 40)).setLayer(LayerEnum.HUD));
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
			zombie.components.add(new Tex("zombie.png", new Point(40, 40)).setLayer(LayerEnum.MOVER));
			world.addEntity(zombie);
		}

	}
}
