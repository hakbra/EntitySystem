package platformer;

import helpers.Point;
import helpers.Time;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Circle;
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
import framework.systems.FollowerSystem;
import framework.systems.KeyInputSystem;
import framework.systems.PhysicsSystem;
import framework.systems.RenderSystem;

public class Main
{
	EntityManager em;
	ArrayList<CoreSystem> systems;
	Time t;

	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}
	
	public Main()
	{
		em = new EntityManager();
		systems = new ArrayList<CoreSystem>();
		t = new Time();
		GLEngine.init();
		
		systems.add(new RenderSystem(em));
		systems.add(new PhysicsSystem(em, Position.class, Velocity.class));
		systems.add(new KeyInputSystem(em, KeyInput.class));
		systems.add(new FollowerSystem(em, Follower.class));
		systems.add(new CollisionSystem(em, Position.class, Circle.class, Velocity.class));
		systems.add(new DamageSystem(em, Damage.class));
		
		Entity player = em.newEntity();
		player.name = "Player";
		em.addComponent(player, new Hero());
		em.addComponent(player, new Circle(40, "Blue"));
		em.addComponent(player, new Position(new Point(1000, 350)));
		em.addComponent(player, new Velocity(new Point(0, 0)));
		em.addComponent(player, new Angle(180));
		em.addComponent(player, new Followable());
		em.addComponent(player, new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S));
		em.addComponent(player, new Gun(10, 5, 10, 500, 20));
		em.addComponent(player, new Health());
		
		Entity circle = em.newEntity();
		circle.name = "circle1";
		em.addComponent(circle, new Circle(50, "Red"));
		em.addComponent(circle, new Position(new Point(700, 200 + 200)));
		em.addComponent(circle, new Obstacle());

		Entity polygon = em.newEntity();
		polygon.name = "polygon";
		em.addComponent(polygon, Polygon.rectangle("Red", new Point(200, 100)));
		em.addComponent(polygon, new Position(new Point(600, 100)));
		em.addComponent(polygon, new Obstacle());

		Entity polygon2 = em.newEntity();
		polygon2.name = "polygon2";
		em.addComponent(polygon2, new Polygon("Red",
				new Point(), new Point(0, 50), new Point(50, 100), new Point(100, 50), new Point(100, 0), new Point(50, -50)));
		em.addComponent(polygon2, new Position(new Point(400, 500)));
		em.addComponent(polygon2, new Obstacle());
		

		for (int i = 0; i < 2; i++) {
			Entity zombie = em.newEntity();
			zombie.name = "Zombie" + i;
			em.addComponent(zombie, new Zombie());
			em.addComponent(zombie, new Circle(20, "White"));
			em.addComponent(zombie, new Position(new Point(200 - i*10, 350 + i*10)));
			em.addComponent(zombie, new Velocity(new Point(0, 0)));
			em.addComponent(zombie, new Health());
			em.addComponent(zombie, new Follower());
			em.addComponent(zombie, new Damage(5, 1000));
		}
	}
	
	public void run()
	{
		while (GLEngine.running())
		{
			GLEngine.clearState();
			GLEngine.prepare2D();
			
			for(CoreSystem s: systems)
				s.run(em);
			
			em.doRemoval();
			
			GLEngine.endRender();
			
			t.sync(60);
		}
	}
}
