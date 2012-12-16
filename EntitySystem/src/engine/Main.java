package engine;

import helpers.Color;
import helpers.Point;
import helpers.State;
import helpers.Time;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import framework.Entity;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.ColorComp;
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
import framework.components.TextureComp;
import framework.components.Trigger;
import framework.components.Velocity;
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

public class Main
{
	World world;
	Time t;

	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}

	public Main()
	{
		GLEngine.init();
		world = new World(State.MENU);
		t = new Time();

		world.addSystem(new HudRenderSystem(world), State.MENU);
		world.addSystem(new MouseInputSystem(world), State.MENU);
		
		world.addSystem(new PlayerInputSystem(world), State.RUN);
		world.addSystem(new CameraSystem(world), State.RUN);
		world.addSystem(new PathSystem(world), State.RUN);
		world.addSystem(new FollowerSystem(world), State.RUN);
		world.addSystem(new PhysicsSystem(world), State.RUN);
		world.addSystem(new CollisionSystem(world), State.RUN);
		
		world.addSystem(new SubLightRenderSystem(world), State.RUN);
		world.addSystem(new LightSystem(world), State.RUN);
		world.addSystem(new SurLightRenderSystem(world), State.RUN);
		world.addSystem(new HudRenderSystem(world), State.RUN);
		
		world.addSystem(new EmitterSystem(world), State.RUN);
		world.addSystem(new MouseInputSystem(world), State.RUN);
		world.addSystem(new KeyInputSystem(world), State.RUN);
		world.addSystem(new TimerSystem(world), State.RUN);

		Entity player = new Entity();
		player.name = "player1";
		player.components.add(new Hero());
		player.components.add(new Circle(25));
		player.components.add(new Position(new Point(300, 250)));
		player.components.add(new Velocity(new Point(0, 0)));
		player.components.add(new Angle(0));
		player.components.add(new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_SPACE));
		player.components.add(new Gun(2, 0, 10, 20, 1, "gun1.png"));
		player.components.add(new Health());
		player.components.add(new Collider(4));
		player.components.add(new Obstacle());
		player.components.add(new Light(300));
		player.components.add(new TextureComp("hero.png"));
		world.addEntityAndID(player, State.RUN);

		Entity worldEntity = new Entity();
		worldEntity.name = "camera";
		worldEntity.components.add(new Position( new Point()));
		worldEntity.components.add(new Polygon( new Point(0, 0), new Point(GLEngine.WIDTH, 0)));
		worldEntity.components.add(new Pathfinder(new Point(0, 0), new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT), 10));
		world.addEntityAndID(worldEntity, State.RUN);
		
		Entity ground = new Entity();
		ground.name = "ground";
		ground.components.add(new Position( new Point(), true));
		ground.components.add(Polygon.rectangle(new Point(GLEngine.WIDTH, GLEngine.HEIGHT)));
		ground.components.add(new ColorComp( new Color(0.4, 0.4, 0.4)));
		world.addEntityAndID(ground, State.RUN);
		
		Entity gun = new Entity();
		gun.name = "gun";
		gun.components.add(new Position(new Point(GLEngine.WIDTH * 2 - 75, 75)));
		gun.components.add(new Circle(30));
		gun.components.add(new Item("gun"));
		gun.components.add(new TextureComp("gun2.png"));
		gun.components.add(new Gun(3, 10, 10, 400, 20, "gun2.png"));
		gun.components.add(new Angle(0));
		gun.components.add(new AngleSpeed(1));
		world.addEntity(gun, State.RUN);
		
		createButtons(world);
		createHealth(world);
		createWalls(world);
	}

	private void createHealth(World world)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		
		points.add( new Point(GLEngine.WIDTH * 2 - 75	, 	GLEngine.HEIGHT - 75));

		for (Point p : points)
		{
			Entity health = new Entity();
			health.name = "health";
			health.components.add(new Circle(15));
			health.components.add(new Position(p));
			health.components.add(new TextureComp("health.png"));
			health.components.add(new Item("health", 100));
			health.components.add(new Angle(0));
			health.components.add(new AngleSpeed(1));
			world.addEntity(health, State.RUN);
		}
	}

	private void createWalls(World world) {
		ArrayList<Point> p = new ArrayList<Point>();

		p.add(  new Point(400, 50)  );
		p.add(  new Point(150, 100)  );

		/**/
		p.add(  new Point(50, 130)  );
		p.add(  new Point(150, 420)  );

		p.add(  new Point(50, 20)  );
		p.add(  new Point(150, 380)  );

		p.add(  new Point(50, 20)  );
		p.add(  new Point(150, 340)  );

		p.add(  new Point(50, 20)  );
		p.add(  new Point(150, 300)  );

		p.add(  new Point(50, 130)  );
		p.add(  new Point(150, 150)  );
		/**/

		p.add(  new Point(400, 50)  );
		p.add(  new Point(150, 550)  );

		p.add(  new Point(400, 50)  );
		p.add(  new Point(730, 100)  );

		/**/
		p.add(  new Point(50, 130)  );
		p.add(  new Point(1080, 420)  );

		p.add(  new Point(50, 20)  );
		p.add(  new Point(1080, 380)  );

		p.add(  new Point(50, 20)  );
		p.add(  new Point(1080, 340)  );

		p.add(  new Point(50, 20)  );
		p.add(  new Point(1080, 300)  );

		p.add(  new Point(50, 130)  );
		p.add(  new Point(1080, 150)  );
		/**/

		p.add(  new Point(400, 50)  );
		p.add(  new Point(730, 550)  );

		p.add(  new Point(600, 50)  );
		p.add(  new Point(340, 335)  );

		p.add(  new Point(50, 300) );
		p.add(  new Point(GLEngine.WIDTH + 50, 0)  );

		p.add(  new Point(50, 300) );
		p.add(  new Point(GLEngine.WIDTH + 50, 420)  );
		

		p.add(  new Point(200, 200) );
		p.add(  new Point(GLEngine.WIDTH + 250, 100)  );

		p.add(  new Point(200, 200) );
		p.add(  new Point(GLEngine.WIDTH + 250, 420)  );

		p.add(  new Point(200, 200) );
		p.add(  new Point(GLEngine.WIDTH + 610, 100)  );

		p.add(  new Point(200, 200) );
		p.add(  new Point(GLEngine.WIDTH + 610, 420)  );

		p.add(  new Point(50, 200) );
		p.add(  new Point(GLEngine.WIDTH*2 - 200, 0)  );

		p.add(  new Point(50, 200) );
		p.add(  new Point(GLEngine.WIDTH*2 - 200, 520)  );

		p.add(  new Point(100, 50) );
		p.add(  new Point(GLEngine.WIDTH*2 - 100, 150)  );

		p.add(  new Point(100, 50) );
		p.add(  new Point(GLEngine.WIDTH*2 - 100, 520)  );

		while (p.size() > 0)
		{
			Entity rectangle = new Entity();
			rectangle.name = "rectangle";
			rectangle.components.add(Polygon.rectangle(p.remove(0)));
			rectangle.components.add(new Position(p.remove(0)));
			rectangle.components.add(new Obstacle());
			rectangle.components.add(new TextureComp("bush.png", new Point(256, 256)));
			world.addEntity(rectangle, State.RUN);
			
		}

		Entity polygon = new Entity();
		polygon.name = "polygon";
		polygon.components.add(new Polygon(new Point(100, 50), new Point(100, -50), new Point(-100, -50), new Point(-100, 50)				));
		polygon.components.add(new Position(new Point(GLEngine.WIDTH*2 - 300, GLEngine.HEIGHT / 2)));
		polygon.components.add(new Obstacle());
		polygon.components.add(new TextureComp("bush.png", new Point(256, 256)));
		polygon.components.add(new Angle(45));
		polygon.components.add(new AngleSpeed(0.2));
		world.addEntity(polygon, State.RUN);

		Entity border = new Entity();
		border.name = "border";
		border.components.add(Polygon.invertedRectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		border.components.add(new Position(new Point(0, 0)));
		border.components.add(new Obstacle());
		world.addEntity(border, State.RUN);

		Entity trigger = new Entity();
		trigger.name = "trigger";
		trigger.components.add(Polygon.rectangle(new Point(150, 150)));
		trigger.components.add(new Position( new Point(GLEngine.WIDTH*2-150, 0)));
		trigger.components.add(new Trigger("spawner"));
		trigger.components.add(new Trigger("spawner"));
		world.addEntity(trigger, State.RUN);
	}
	
	private void createButtons(World world)
	{

		Entity menuButton = new Entity();
		menuButton.name = "button";
		menuButton.components.add(Polygon.rectangle(new Point(100, 50)));
		menuButton.components.add(new Position(new Point(150, 650)));
		menuButton.components.add(new Button("Menu"));
		menuButton.components.add(new TextureComp("button.png"));
		world.addEntity(menuButton, State.RUN);

		Entity exitButton2 = new Entity();
		exitButton2.name = "exitButton";
		exitButton2.components.add(Polygon.rectangle(new Point(100, 50)));
		exitButton2.components.add(new Position(new Point(25, 650)));
		exitButton2.components.add(new Button("Exit"));
		exitButton2.components.add(new TextureComp("button.png"));
		world.addEntity(exitButton2, State.RUN);

		Entity zombieButton = new Entity();
		zombieButton.name = "zombieButton";
		zombieButton.components.add(Polygon.rectangle(new Point(100, 50)));
		zombieButton.components.add(new Position(new Point(400, 650)));
		zombieButton.components.add(new Button("Zombies"));
		zombieButton.components.add(new TextureComp("button.png"));
		world.addEntity(zombieButton, State.RUN);

		Entity screenButton = new Entity();
		screenButton.name = "screenButton";
		screenButton.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(525, 650)));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new TextureComp("button.png"));
		world.addEntity(screenButton, State.RUN);

		Entity screenButton2 = new Entity();
		screenButton2.name = "screenButton2";
		screenButton2.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton2.components.add(new Position(new Point(GLEngine.WIDTH - 125, 25)));
		screenButton2.components.add(new Button("Screen"));
		screenButton2.components.add(new TextureComp("button.png"));
		world.addEntity(screenButton2, State.MENU);

		Entity lightButton = new Entity();
		lightButton.name = "lightButton";
		lightButton.components.add(Polygon.rectangle(new Point(100, 50)));
		lightButton.components.add(new Position(new Point(275, 650)));
		lightButton.components.add(new Button("Lights"));
		lightButton.components.add(new TextureComp("button.png"));
		world.addEntity(lightButton, State.RUN);

		Entity runButton = new Entity();
		runButton.name = "runButton";
		runButton.components.add(Polygon.rectangle(new Point(200, 100)));
		runButton.components.add(new Position(new Point(500, 300)));
		runButton.components.add(new Button("Play"));
		runButton.components.add(new TextureComp("button.png"));
		world.addEntity(runButton, State.MENU);

		Entity exitButton = new Entity();
		exitButton.name = "exitButton";
		exitButton.components.add(Polygon.rectangle(new Point(200, 100)));
		exitButton.components.add(new Position(new Point(500, 150)));
		exitButton.components.add(new Button("Exit"));
		exitButton.components.add(new TextureComp("button.png"));
		world.addEntity(exitButton, State.MENU);
	}

	public void run()
	{
		while (GLEngine.running() && world.run())
		{
			GLEngine.clearState();
			GLEngine.prepare2D();

			world.runSystems();

			GLEngine.endRender();

			t.sync(60);
		}
	}
}
