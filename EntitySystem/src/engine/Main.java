package engine;

import helpers.Color;
import helpers.Point;
import helpers.State;
import helpers.Time;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import framework.Entity;
import framework.StateManager;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.ColorComp;
import framework.components.Followable;
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
	StateManager sm;
	Time t;

	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}

	public Main()
	{
		sm = new StateManager(State.MENU);
		t = new Time();
		GLEngine.init();

		// Menu-state
		sm.addSystem(State.MENU, RenderSystem.class);
		sm.addSystem(State.MENU, MouseInputSystem.class);

		Entity runButton = new Entity();
		runButton.name = "runButton";
		sm.addComponent(State.MENU, runButton, Polygon.rectangle(new Point(200, 100)));
		sm.addComponent(State.MENU, runButton, new Position(new Point(500, 300)));
		sm.addComponent(State.MENU, runButton, new Button("Play"));
		sm.addComponent(State.MENU, runButton, new TextureComp("button.png"));

		Entity exitButton = new Entity();
		exitButton.name = "exitButton";
		sm.addComponent(State.MENU, exitButton, Polygon.rectangle(new Point(200, 100)));
		sm.addComponent(State.MENU, exitButton, new Position(new Point(500, 150)));
		sm.addComponent(State.MENU, exitButton, new Button("Exit"));
		sm.addComponent(State.MENU, exitButton, new TextureComp("button.png"));

		//Run-state
		sm.addSystem(State.RUN, PlayerInputSystem.class);
		sm.addSystem(State.RUN, CameraSystem.class);
		sm.addSystem(State.RUN, PathSystem.class);
		sm.addSystem(State.RUN, FollowerSystem.class);
		sm.addSystem(State.RUN, PhysicsSystem.class);
		sm.addSystem(State.RUN, CollisionSystem.class);
		sm.addSystem(State.RUN, RenderSystem.class);
		sm.addSystem(State.RUN, EmitterSystem.class);
		sm.addSystem(State.RUN, MouseInputSystem.class);
		sm.addSystem(State.RUN, KeyInputSystem.class);
		sm.addSystem(State.RUN, TimerSystem.class);


		Entity player = new Entity();
		player.name = "player1";
		sm.addComponent(State.RUN, player, new Hero());
		sm.addComponent(State.RUN, player, new Circle(25));
		sm.addComponent(State.RUN, player, new Position(new Point(300, 250)));
		sm.addComponent(State.RUN, player, new Velocity(new Point(0, 0)));
		sm.addComponent(State.RUN, player, new Angle(0));
		sm.addComponent(State.RUN, player, new Followable());
		sm.addComponent(State.RUN, player, new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_SPACE));
		sm.addComponent(State.RUN, player, new Gun(1, 0, 10, 20, 1));
		sm.addComponent(State.RUN, player, new Health());
		sm.addComponent(State.RUN, player, new Collider(4));
		sm.addComponent(State.RUN, player, new Obstacle());
		sm.addComponent(State.RUN, player, new Light(300));
		sm.addComponent(State.RUN, player, new TextureComp("hero.png"));
		sm.addStringID(State.RUN, player);

		addHealth(sm);

		Entity world = new Entity();
		world.name = "world";
		sm.addComponent(State.RUN, world, new Position( new Point()));
		sm.addComponent(State.RUN, world, new Polygon( new Point(0, 0), new Point(GLEngine.WIDTH, 0)));
		sm.addComponent(State.RUN, world, new Pathfinder(new Point(0, 0), new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT), 10));
		sm.addStringID(State.RUN, world);

		createMaze(sm);

		Entity menuButton = new Entity();
		menuButton.name = "button";
		sm.addComponent(State.RUN, menuButton, Polygon.rectangle(new Point(100, 50)));
		sm.addComponent(State.RUN, menuButton, new Position(new Point(1150, 650)));
		sm.addComponent(State.RUN, menuButton, new Button("Menu"));
		sm.addComponent(State.RUN, menuButton, new TextureComp("button.png"));

		//*
		Entity exitButton2 = new Entity();
		exitButton2.name = "exitButton";
		sm.addComponent(State.RUN, exitButton2, Polygon.rectangle(new Point(100, 50)));
		sm.addComponent(State.RUN, exitButton2, new Position(new Point(25, 650)));
		sm.addComponent(State.RUN, exitButton2, new Button("Exit"));
		sm.addComponent(State.RUN, exitButton2, new TextureComp("button.png"));
		/**/

		Entity zombieButton = new Entity();
		zombieButton.name = "zombieButton";
		sm.addComponent(State.RUN, zombieButton, Polygon.rectangle(new Point(100, 50)));
		sm.addComponent(State.RUN, zombieButton, new Position(new Point(590, 650)));
		sm.addComponent(State.RUN, zombieButton, new Button("Zombie"));
		sm.addComponent(State.RUN, zombieButton, new TextureComp("button.png"));

		Entity screenButton = new Entity();
		screenButton.name = "screenButton";
		sm.addComponent(State.RUN, screenButton, Polygon.rectangle(new Point(100, 50)));
		sm.addComponent(State.RUN, screenButton, new Position(new Point(1150, 25)));
		sm.addComponent(State.RUN, screenButton, new Button("Screen"));
		sm.addComponent(State.RUN, screenButton, new TextureComp("button.png"));

		sm.addComponent(State.MENU, screenButton, Polygon.rectangle(new Point(100, 50)));
		sm.addComponent(State.MENU, screenButton, new Position(new Point(1150, 25)));
		sm.addComponent(State.MENU, screenButton, new Button("Screen"));
		sm.addComponent(State.MENU, screenButton, new TextureComp("button.png"));
	}

	private void addHealth(StateManager sm)
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
			sm.addComponent(State.RUN, health, new Circle(15));
			sm.addComponent(State.RUN, health, new Position(p));
			sm.addComponent(State.RUN, health, new TextureComp("health.png"));
			sm.addComponent(State.RUN, health, new Item("health", 100));
			sm.addComponent(State.RUN, health, new Angle(0));
			sm.addComponent(State.RUN, health, new AngleSpeed(1));
		}
	}

	private void createMaze(StateManager sm) {
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
			sm.addComponent(State.RUN, rectangle, Polygon.rectangle(p.remove(0)));
			sm.addComponent(State.RUN, rectangle, new Position(p.remove(0)));
			sm.addComponent(State.RUN, rectangle, new Obstacle());
			sm.addComponent(State.RUN, rectangle, new TextureComp("bush.png"));
		}

		Entity polygon = new Entity();
		polygon.name = "polygon";
		sm.addComponent(State.RUN, polygon, new Polygon(
				new Point(0, 100), new Point(100, 0), new Point(0, -100), new Point(-100, 0)
				));
		sm.addComponent(State.RUN, polygon, new Position(new Point(GLEngine.WIDTH*2 - 250, GLEngine.HEIGHT / 2)));
		sm.addComponent(State.RUN, polygon, new Obstacle());
		sm.addComponent(State.RUN, polygon, new TextureComp("bush.png"));

		Entity border = new Entity();
		border.name = "border";
		sm.addComponent(State.RUN, border, Polygon.invertedRectangle(new Point(GLEngine.WIDTH*2, GLEngine.HEIGHT)));
		sm.addComponent(State.RUN, border, new Position(new Point(0, 0)));
		sm.addComponent(State.RUN, border, new Obstacle());
	}

	public void run()
	{
		while (GLEngine.running() && sm.run())
		{
			GLEngine.clearState();
			GLEngine.prepare2D();

			sm.runSystems();

			GLEngine.endRender();

			t.sync(60);
		}
	}
}
