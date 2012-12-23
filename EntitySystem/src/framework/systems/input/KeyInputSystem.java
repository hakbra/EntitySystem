package framework.systems.input;

import helpers.Point;

import org.lwjgl.input.Keyboard;


import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.Layer;
import framework.State;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Gun;
import framework.components.Health;
import framework.components.Hero;
import framework.components.KeyInput;
import framework.components.Light;
import framework.components.Obstacle;
import framework.components.Position;
import framework.components.Tex;
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
		if (Keyboard.isKeyDown(Keyboard.KEY_2) && em.getByStringID("Player 2") == null && world.state == State.LEVEL1)
		{
			Entity player = new Entity();
			player.name = "Player 2";
			player.layer = Layer.MOVER;
			em.addComponent(player, new Hero());
			em.addComponent(player, new Circle(20));
			em.addComponent(player, new Position(new Point(1000, 450)));
			em.addComponent(player, new Velocity(new Point(0, 0)));
			em.addComponent(player, new Angle(180));
			em.addComponent(player, new AngleSpeed(0));
			em.addComponent(player, new KeyInput(Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_RCONTROL));
			em.addComponent(player, new Gun(2, 2, 10, 50, 2, "gun1.png"));
			em.addComponent(player, new Health());
			em.addComponent(player, new Collider(4));
			em.addComponent(player, new Obstacle());
			em.addComponent(player, new Light(300));
			em.addComponent(player, new Tex("man.png", new Point(1, 1), new Point(0, 0)));
			em.addStringID(player);
		}
	}
}
