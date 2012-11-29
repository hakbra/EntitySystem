package platformer;

import helpers.Color;
import helpers.Point;
import helpers.State;
import helpers.Time;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Button;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.Followable;
import framework.components.Follower;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.KeyInput;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Velocity;
import framework.components.Zombie;
import framework.systems.CollisionSystem;
import framework.systems.DamageSystem;
import framework.systems.EmitterSystem;
import framework.systems.FollowerSystem;
import framework.systems.GameRenderSystem;
import framework.systems.MenuInputSystem;
import framework.systems.MenuRenderSystem;
import framework.systems.PhysicsSystem;
import framework.systems.PlayerInputSystem;

public class Main
{
	EntityManager em;
	ArrayList<ArrayList<CoreSystem>> systems;
	Time t;

	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}
	
	public Main()
	{
		em = new EntityManager();
		systems = new ArrayList<ArrayList<CoreSystem>>();
		systems.add(new ArrayList<CoreSystem>());
		systems.add(new ArrayList<CoreSystem>());
		t = new Time();
		GLEngine.init();
		
		systems.get(State.MENU.c()).add(new MenuRenderSystem(em));
		systems.get(State.MENU.c()).add(new MenuInputSystem(em));
		
		systems.get(State.RUN.c()).add(new GameRenderSystem(em));
		systems.get(State.RUN.c()).add(new PhysicsSystem(em));
		systems.get(State.RUN.c()).add(new PlayerInputSystem(em));
		systems.get(State.RUN.c()).add(new FollowerSystem(em));
		systems.get(State.RUN.c()).add(new CollisionSystem(em));
		systems.get(State.RUN.c()).add(new DamageSystem(em));
		systems.get(State.RUN.c()).add(new EmitterSystem(em));
		
		Entity player = new Entity();
		player.name = "Player";
		em.addComponent(player, new Hero());
		em.addComponent(player, new Circle(40, Color.BLUE));
		em.addComponent(player, new Position(new Point(1000, 350)));
		em.addComponent(player, new Velocity(new Point(0, 0)));
		em.addComponent(player, new Angle(180));
		em.addComponent(player, new Followable());
		em.addComponent(player, new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S));
		em.addComponent(player, new Gun(10, 5, 10, 500, 20));
		em.addComponent(player, new Health());
		em.addComponent(player, new Collider());
		em.addComponent(player, new Obstacle());
		
		Entity circle = new Entity();
		circle.name = "circle1";
		em.addComponent(circle, new Circle(50, Color.RED));
		em.addComponent(circle, new Position(new Point(700, 200 + 200)));
		em.addComponent(circle, new Obstacle());

		Entity rectangle = new Entity();
		rectangle.name = "rectangle";
		em.addComponent(rectangle, Polygon.rectangle(Color.RED, new Point(200, 100)));
		em.addComponent(rectangle, new Position(new Point(600, 100)));
		em.addComponent(rectangle, new Obstacle());

		Entity polygon2 = new Entity();
		polygon2.name = "polygon2";
		em.addComponent(polygon2, new Polygon(Color.RED,
				new Point(), new Point(0, 50), new Point(50, 100), new Point(100, 50), new Point(100, 0), new Point(50, -50)));
		em.addComponent(polygon2, new Position(new Point(400, 500)));
		em.addComponent(polygon2, new Obstacle());
		

		for (int i = 0; i < 20; i++) {
			Entity zombie = new Entity();
			zombie.name = "Zombie" + i;
			em.addComponent(zombie, new Zombie());
			em.addComponent(zombie, new Circle(20, Color.YELLOW));
			em.addComponent(zombie, new Position(new Point(200 - i*10, 350 + i*10)));
			em.addComponent(zombie, new Velocity(new Point(0, 0)));
			em.addComponent(zombie, new Health());
			em.addComponent(zombie, new Follower());
			em.addComponent(zombie, new Damage(5, 1000));
			em.addComponent(zombie, new Obstacle());
			em.addComponent(zombie, new Collider());
		}

		Entity button = new Entity();
		button.name = "button";
		em.addComponent(button, new Polygon(Color.GREEN,
				new Point(), new Point(0, 50), new Point(100, 50), new Point(100, 0)));
		em.addComponent(button, new Position(new Point(600, 300)));
		em.addComponent(button, new Button(State.RUN));
	}
	
	public void run()
	{
		while (GLEngine.running())
		{
			GLEngine.clearState();
			GLEngine.prepare2D();
			
			for(CoreSystem s: systems.get(em.state.c()))
				s.run(em);
			
			em.doRemoval();
			
			GLEngine.endRender();
			
			t.sync(60);
		}
	}
}
