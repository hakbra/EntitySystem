package engine;

import helpers.Color;
import helpers.Point;
import helpers.State;
import helpers.Time;

import org.lwjgl.input.Keyboard;

import framework.Entity;
import framework.StateManager;
import framework.components.Angle;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Followable;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.KeyInput;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.StateButton;
import framework.components.StringButton;
import framework.components.Velocity;
import framework.systems.CollisionSystem;
import framework.systems.DamageSystem;
import framework.systems.EmitterSystem;
import framework.systems.FollowerSystem;
import framework.systems.GameRenderSystem;
import framework.systems.MenuInputSystem;
import framework.systems.PhysicsSystem;
import framework.systems.PlayerInputSystem;
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
		sm.addSystem(State.MENU, MenuInputSystem.class);
		sm.addSystem(State.MENU, GameRenderSystem.class);

		Entity runButton = new Entity();
		runButton.name = "runButton";
		sm.addComponent(State.MENU, runButton, Polygon.rectangle(Color.GREEN, new Point(200, 100)));
		sm.addComponent(State.MENU, runButton, new Position(new Point(500, 300)));
		sm.addComponent(State.MENU, runButton, new StateButton(State.RUN));
		
		Entity exitButton = new Entity();
		exitButton.name = "exitButton";
		sm.addComponent(State.MENU, exitButton, Polygon.rectangle(Color.GREEN, new Point(200, 100)));
		sm.addComponent(State.MENU, exitButton, new Position(new Point(500, 150)));
		sm.addComponent(State.MENU, exitButton, new StateButton(State.EXIT));
		
		//Run-state
		sm.addSystem(State.RUN, GameRenderSystem.class);
		sm.addSystem(State.RUN, PhysicsSystem.class);
		sm.addSystem(State.RUN, PlayerInputSystem.class);
		sm.addSystem(State.RUN, FollowerSystem.class);
		sm.addSystem(State.RUN, CollisionSystem.class);
		sm.addSystem(State.RUN, DamageSystem.class);
		sm.addSystem(State.RUN, EmitterSystem.class);
		sm.addSystem(State.RUN, MenuInputSystem.class);
		sm.addSystem(State.RUN, TimerSystem.class);
		
		Entity player = new Entity();
		player.name = "Player";
		sm.addComponent(State.RUN, player, new Hero());
		sm.addComponent(State.RUN, player, new Circle(40, Color.BLUE));
		sm.addComponent(State.RUN, player, new Position(new Point(1000, 350)));
		sm.addComponent(State.RUN, player, new Velocity(new Point(0, 0)));
		sm.addComponent(State.RUN, player, new Angle(180));
		sm.addComponent(State.RUN, player, new Followable());
		sm.addComponent(State.RUN, player, new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S));
		sm.addComponent(State.RUN, player, new Gun(10, 5, 10, 500, 20));
		sm.addComponent(State.RUN, player, new Health());
		sm.addComponent(State.RUN, player, new Collider());
		sm.addComponent(State.RUN, player, new Obstacle());
		
		Entity circle = new Entity();
		circle.name = "circle1";
		sm.addComponent(State.RUN, circle, new Circle(50, Color.RED));
		sm.addComponent(State.RUN, circle, new Position(new Point(700, 200 + 200)));
		sm.addComponent(State.RUN, circle, new Obstacle());

		Entity rectangle = new Entity();
		rectangle.name = "rectangle";
		sm.addComponent(State.RUN, rectangle, Polygon.rectangle(Color.RED, new Point(200, 100)));
		sm.addComponent(State.RUN, rectangle, new Position(new Point(600, 100)));
		sm.addComponent(State.RUN, rectangle, new Obstacle());

		Entity polygon = new Entity();
		polygon.name = "polygon";
		sm.addComponent(State.RUN, polygon, new Polygon(Color.RED,
				new Point(), new Point(0, 50), new Point(50, 100), new Point(100, 50), new Point(100, 0), new Point(50, -50)));
		sm.addComponent(State.RUN, polygon, new Position(new Point(400, 500)));
		sm.addComponent(State.RUN, polygon, new Obstacle());

		Entity menuButton = new Entity();
		menuButton.name = "button";
		sm.addComponent(State.RUN, menuButton, Polygon.rectangle(Color.GREEN, new Point(100, 50)));
		sm.addComponent(State.RUN, menuButton, new Position(new Point(1150, 650)));
		sm.addComponent(State.RUN, menuButton, new StateButton(State.MENU));

		Entity exitButton2 = new Entity();
		exitButton2.name = "exitButton";
		sm.addComponent(State.RUN, exitButton2, Polygon.rectangle(Color.GREEN, new Point(100, 50)));
		sm.addComponent(State.RUN, exitButton2, new Position(new Point(50, 650)));
		sm.addComponent(State.RUN, exitButton2, new StateButton(State.EXIT));
		
		Entity zombieButton = new Entity();
		zombieButton.name = "zombieButton";
		sm.addComponent(State.RUN, zombieButton, Polygon.rectangle(Color.GREEN, new Point(100, 50)));
		sm.addComponent(State.RUN, zombieButton, new Position(new Point(600, 650)));
		sm.addComponent(State.RUN, zombieButton, new StringButton("Zombie"));
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
