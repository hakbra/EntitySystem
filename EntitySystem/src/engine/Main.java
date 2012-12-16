package engine;

import helpers.Point;
import helpers.State;
import helpers.Time;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Collider;
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
import framework.components.Velocity;
import framework.systems.CameraSystem;
import framework.systems.CollisionSystem;
import framework.systems.EmitterSystem;
import framework.systems.FollowerSystem;
import framework.systems.KeyInputSystem;
import framework.systems.MouseInputSystem;
import framework.systems.PathSystem;
import framework.systems.PhysicsSystem;
import framework.systems.PlayerInputSystem;
import framework.systems.RenderSystem;
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

		world.addSystem(new RenderSystem(world), State.MENU);
		world.addSystem(new MouseInputSystem(world), State.MENU);
		
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
		
		world.addSystem(new PlayerInputSystem(world), State.RUN);
		world.addSystem(new CameraSystem(world), State.RUN);
		world.addSystem(new PathSystem(world), State.RUN);
		world.addSystem(new FollowerSystem(world), State.RUN);
		world.addSystem(new PhysicsSystem(world), State.RUN);
		world.addSystem(new CollisionSystem(world), State.RUN);
		world.addSystem(new RenderSystem(world), State.RUN);
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
		player.components.add(new Gun(1, 0, 10, 20, 1));
		player.components.add(new Health());
		player.components.add(new Collider(4));
		player.components.add(new Obstacle());
		player.components.add(new Light(300));
		player.components.add(new TextureComp("hero.png"));
		world.addEntityAndID(player, State.RUN);

		addHealth(world);

		Entity worldEntity = new Entity();
		worldEntity.name = "world";
		worldEntity.components.add(new Position( new Point()));
		worldEntity.components.add(new Polygon( new Point(0, 0), new Point(GLEngine.WIDTH, 0)));
		worldEntity.components.add(new Pathfinder(new Point(0, 0), new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT), 10));
		world.addEntityAndID(worldEntity, State.RUN);

		createMaze(world);

		Entity menuButton = new Entity();
		menuButton.name = "button";
		menuButton.components.add(Polygon.rectangle(new Point(100, 50)));
		menuButton.components.add(new Position(new Point(1150, 650)));
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
		zombieButton.components.add(new Position(new Point(590, 650)));
		zombieButton.components.add(new Button("Zombie"));
		zombieButton.components.add(new TextureComp("button.png"));
		world.addEntity(zombieButton, State.RUN);

		Entity screenButton = new Entity();
		screenButton.name = "screenButton";
		screenButton.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(1150, 25)));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new TextureComp("button.png"));
		world.addEntity(screenButton, State.RUN);
		world.addEntity(screenButton, State.MENU);
	}

	private void addHealth(World world)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		
		points.add( new Point(75, 					GLEngine.HEIGHT / 2));
		points.add( new Point(GLEngine.WIDTH - 75, 	GLEngine.HEIGHT / 2));
		

		points.add( new Point(GLEngine.WIDTH * 2 - 50, 	GLEngine.HEIGHT - 50));
		points.add( new Point(GLEngine.WIDTH * 2 - 50, 	50));

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

	private void createMaze(World world) {
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

		/*
		p.add(  new Point(GLEngine.WIDTH*2, 50)  );  //Bottom
		p.add(  new Point(0, -50)  );

		p.add(  new Point(50, GLEngine.HEIGHT)  ); //LEFT
		p.add(  new Point(-50, 0)  );

		p.add(  new Point(50, GLEngine.HEIGHT)  ); //Right
		p.add(  new Point(GLEngine.WIDTH*2, 0)  );

		p.add(  new Point(GLEngine.WIDTH*2, 50)  );  //Top
		p.add(  new Point(0, GLEngine.HEIGHT)  );
		*/

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
			rectangle.components.add(new TextureComp("bush.png"));
			world.addEntity(rectangle, State.RUN);
			
		}

		Entity polygon = new Entity();
		polygon.name = "polygon";
		polygon.components.add(new Polygon(new Point(0, 100), new Point(100, 0), new Point(0, -100), new Point(-100, 0)				));
		polygon.components.add(new Position(new Point(GLEngine.WIDTH*2 - 250, GLEngine.HEIGHT / 2)));
		polygon.components.add(new Obstacle());
		polygon.components.add(new TextureComp("bush.png"));
		polygon.components.add(new Angle(45));
		polygon.components.add(new AngleSpeed(0.2));
		world.addEntity(polygon, State.RUN);

		Entity border = new Entity();
		border.name = "border";
		border.components.add(Polygon.invertedRectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		border.components.add(new Position(new Point(0, 0)));
		border.components.add(new Obstacle());
		world.addEntity(border, State.RUN);
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
