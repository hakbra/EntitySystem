package engine;

import helpers.MyColor;
import helpers.Point;
import helpers.State;
import helpers.Time;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import framework.Entity;
import framework.StateManager;
import framework.components.Angle;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Followable;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.KeyInput;
import framework.components.Light;
import framework.components.Obstacle;
import framework.components.Pathfinder;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.TextureComp;
import framework.components.Velocity;
import framework.systems.CollisionSystem;
import framework.systems.EmitterSystem;
import framework.systems.FollowerSystem;
import framework.systems.MenuInputSystem;
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
		sm = new StateManager(State.RUN);
		t = new Time();
		GLEngine.init();

		// Menu-state
		sm.addSystem(State.MENU, RenderSystem.class);
		sm.addSystem(State.MENU, MenuInputSystem.class);

		Entity runButton = new Entity();
		runButton.name = "runButton";
		sm.addComponent(State.MENU, runButton, Polygon.rectangle(new MyColor(0, 1, 0, 0.5), new Point(200, 100)));
		sm.addComponent(State.MENU, runButton, new Position(new Point(500, 300)));
		sm.addComponent(State.MENU, runButton, new Button("Play"));

		Entity exitButton = new Entity();
		exitButton.name = "exitButton";
		sm.addComponent(State.MENU, exitButton, Polygon.rectangle(new MyColor(0, 1, 0, 0.5), new Point(200, 100)));
		sm.addComponent(State.MENU, exitButton, new Position(new Point(500, 150)));
		sm.addComponent(State.MENU, exitButton, new Button("Exit"));

		//Run-state
		sm.addSystem(State.RUN, RenderSystem.class);
		sm.addSystem(State.RUN, PhysicsSystem.class);
		sm.addSystem(State.RUN, PlayerInputSystem.class);
		sm.addSystem(State.RUN, FollowerSystem.class);
		sm.addSystem(State.RUN, CollisionSystem.class);
		sm.addSystem(State.RUN, EmitterSystem.class);
		sm.addSystem(State.RUN, MenuInputSystem.class);
		sm.addSystem(State.RUN, TimerSystem.class);
		sm.addSystem(State.RUN, PathSystem.class);


		Entity player = new Entity();
		player.name = "Player";
		sm.addComponent(State.RUN, player, new Hero());
		sm.addComponent(State.RUN, player, new Circle(25, MyColor.BLUE));
		sm.addComponent(State.RUN, player, new Position(new Point(1000, 250)));
		sm.addComponent(State.RUN, player, new Velocity(new Point(0, 0)));
		sm.addComponent(State.RUN, player, new Angle(180));
		sm.addComponent(State.RUN, player, new Followable());
		sm.addComponent(State.RUN, player, new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_SPACE));
		sm.addComponent(State.RUN, player, new Gun(2, 15, 10, 500, 20));
		sm.addComponent(State.RUN, player, new Health());
		sm.addComponent(State.RUN, player, new Collider());
		sm.addComponent(State.RUN, player, new Obstacle());
		sm.addComponent(State.RUN, player, new Light(400));
		sm.addComponent(State.RUN, player, new TextureComp("test.png"));

		//*
		Entity player2 = new Entity();
		player2.name = "player2";
		sm.addComponent(State.RUN, player2, new Hero());
		sm.addComponent(State.RUN, player2, new Circle(25, MyColor.YELLOW));
		sm.addComponent(State.RUN, player2, new Position(new Point(1000, 450)));
		sm.addComponent(State.RUN, player2, new Velocity(new Point(0, 0)));
		sm.addComponent(State.RUN, player2, new Angle(180));
		sm.addComponent(State.RUN, player2, new Followable());
		sm.addComponent(State.RUN, player2, new KeyInput(
				Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_RCONTROL));
		sm.addComponent(State.RUN, player2, new Gun(2, 2, 10, 50, 2));
		sm.addComponent(State.RUN, player2, new Health());
		sm.addComponent(State.RUN, player2, new Collider());
		sm.addComponent(State.RUN, player2, new Obstacle());
		sm.addComponent(State.RUN, player2, new Light(400));
		/**/
		
		Entity path = new Entity();
		path.name = "path";
		sm.addComponent(State.RUN, path, new Pathfinder(GLEngine.WIDTH, GLEngine.HEIGHT, 15));

		createMaze(sm);

		Entity menuButton = new Entity();
		menuButton.name = "button";
		sm.addComponent(State.RUN, menuButton, Polygon.rectangle(new MyColor(0, 1, 0, 0.5), new Point(100, 50)));
		sm.addComponent(State.RUN, menuButton, new Position(new Point(1150, 650)));
		sm.addComponent(State.RUN, menuButton, new Button("Menu"));

		//*
		Entity exitButton2 = new Entity();
		exitButton2.name = "exitButton";
		sm.addComponent(State.RUN, exitButton2, Polygon.rectangle(new MyColor(0, 1, 0, 0.5), new Point(100, 50)));
		sm.addComponent(State.RUN, exitButton2, new Position(new Point(25, 650)));
		sm.addComponent(State.RUN, exitButton2, new Button("Exit"));
		/**/

		Entity zombieButton = new Entity();
		zombieButton.name = "zombieButton";
		sm.addComponent(State.RUN, zombieButton, Polygon.rectangle(new MyColor(0, 1, 0, 0.5), new Point(100, 50)));
		sm.addComponent(State.RUN, zombieButton, new Position(new Point(590, 650)));
		sm.addComponent(State.RUN, zombieButton, new Button("Zombie"));

		Entity screenButton = new Entity();
		screenButton.name = "screenButton";
		sm.addComponent(State.RUN, screenButton, Polygon.rectangle(new MyColor(0, 1, 0, 0.5), new Point(100, 50)));
		sm.addComponent(State.RUN, screenButton, new Position(new Point(1150, 25)));
		sm.addComponent(State.RUN, screenButton, new Button("Screen"));
		
		sm.addComponent(State.MENU, screenButton, Polygon.rectangle(new MyColor(0, 1, 0, 0.5), new Point(100, 50)));
		sm.addComponent(State.MENU, screenButton, new Position(new Point(1150, 25)));
		sm.addComponent(State.MENU, screenButton, new Button("Screen"));
	}

	private void createMaze(StateManager sm) {
		ArrayList<Point> p = new ArrayList<Point>();
		
		p.add(  new Point(400, 50)  );
		p.add(  new Point(150, 100)  );

		p.add(  new Point(50, 400)  );
		p.add(  new Point(150, 150)  );

		p.add(  new Point(400, 50)  );
		p.add(  new Point(150, 550)  );

		p.add(  new Point(400, 50)  );
		p.add(  new Point(730, 100)  );

		p.add(  new Point(50, 400)  );
		p.add(  new Point(1080, 150)  );

		p.add(  new Point(400, 50)  );
		p.add(  new Point(730, 550)  );

		p.add(  new Point(600, 50)  );
		p.add(  new Point(300, 335)  );

		p.add(  new Point(GLEngine.WIDTH, 50)  );  //Left
		p.add(  new Point(0, -50)  );

		p.add(  new Point(50, GLEngine.HEIGHT)  ); //LEFT
		p.add(  new Point(-50, 0)  );

		p.add(  new Point(50, GLEngine.HEIGHT)  ); //Right
		p.add(  new Point(GLEngine.WIDTH, 0)  );

		p.add(  new Point(GLEngine.WIDTH, 50)  );  //Top
		p.add(  new Point(0, GLEngine.HEIGHT)  );

		while (p.size() > 0)
		{
			Entity rectangle = new Entity();
			rectangle.name = "rectangle";
			sm.addComponent(State.RUN, rectangle, Polygon.rectangle(new MyColor(0.5f, 0, 0), p.remove(0)));
			sm.addComponent(State.RUN, rectangle, new Position(p.remove(0)));
			sm.addComponent(State.RUN, rectangle, new Obstacle());
		}
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
