package framework.systems;

import helpers.Point;

import org.lwjgl.input.Keyboard;

import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Angle;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.KeyInput;
import framework.components.Light;
import framework.components.Obstacle;
import framework.components.Position;
import framework.components.TextureComp;
import framework.components.Velocity;




public class KeyInputSystem extends CoreSystem{
	
	public static float s = 2f;

	public KeyInputSystem(World w)
	{
		super(w);
	}
	@Override
	public void run(EntityManager em)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_1) && em.getByStringID("player1") == null)
		{
			Entity player = new Entity();
			player.name = "player1";
			em.addComponent(player, new Hero());
			em.addComponent(player, new Circle(25));
			em.addComponent(player, new Position(new Point(300, 250)));
			em.addComponent(player, new Velocity(new Point(0, 0)));
			em.addComponent(player, new Angle(0));
			em.addComponent(player, new KeyInput(Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_SPACE));
			em.addComponent(player, new Gun(2, 2, 10, 50, 2));
			em.addComponent(player, new Health());
			em.addComponent(player, new Collider(4));
			em.addComponent(player, new Obstacle());
			em.addComponent(player, new Light(400));
			em.addComponent(player, new TextureComp("hero.png"));
			em.addStringID(player);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_2) && em.getByStringID("player2") == null)
		{
			Entity player = new Entity();
			player.name = "player2";
			em.addComponent(player, new Hero());
			em.addComponent(player, new Circle(25));
			em.addComponent(player, new Position(new Point(1000, 450)));
			em.addComponent(player, new Velocity(new Point(0, 0)));
			em.addComponent(player, new Angle(180));
			em.addComponent(player, new KeyInput(Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_RCONTROL));
			em.addComponent(player, new Gun(2, 2, 10, 50, 2));
			em.addComponent(player, new Health());
			em.addComponent(player, new Collider(4));
			em.addComponent(player, new Obstacle());
			em.addComponent(player, new Light(400));
			em.addComponent(player, new TextureComp("hero.png"));
			em.addStringID(player);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_T))
		{
			Entity player = em.getByStringID("player1");
			if (player != null)
			{
				Point pos = em.getComponent(player, Position.class).position;
				pos.set(GLEngine.WIDTH*2 - 50, GLEngine.HEIGHT / 2);
			}
		}
	}
}
