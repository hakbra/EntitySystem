package framework.systems.input;

import helpers.Point;

import org.lwjgl.input.Keyboard;

import zombies.states.MessageState;


import engine.GLEngine;
import framework.CoreSystem;
import framework.CoreEntity;
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
import framework.components.Message;
import framework.components.Obstacle;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.components.Velocity;
import framework.enums.LayerEnum;
import framework.enums.StateEnum;
import framework.managers.EntityManager;




public class KeyInputSystem extends CoreSystem{
	
	public static float s = 2f;

	public KeyInputSystem(World w)
	{
		super(w);
	}
	@Override
	public void run(EntityManager em)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_2) && em.getByStringID("Player 2") == null && world.currentState == StateEnum.LEVEL1)
		{
			CoreEntity player = new CoreEntity();
			player.name = "Player 2";
			player.layer = LayerEnum.MOVER;
			em.addComponent(player, new Hero());
			em.addComponent(player, new Circle(20));
			em.addComponent(player, new Position(new Point(900, 450)));
			em.addComponent(player, new Velocity(new Point(0, 0)));
			em.addComponent(player, new Angle(180));
			em.addComponent(player, new AngleSpeed(0));
			em.addComponent(player, new KeyInput(Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_RCONTROL, Keyboard.KEY_RSHIFT));
			em.addComponent(player, new Gun(2, 2, 10, 50, 2, "gun1.png"));
			em.addComponent(player, new Health());
			em.addComponent(player, new Collider(4));
			em.addComponent(player, new Obstacle());
			em.addComponent(player, new Light(300));
			em.addComponent(player, new Tex("man.png", new Point(1, 1), new Point(0, 0)));
			em.addStringID(player);

			CoreEntity msg = new CoreEntity();
			msg.name = "msg";
			em.addComponent(msg, new Message("PLAYER TWO READY"));
			em.addComponent(msg, new Timer(3000));
			em.addComponent(msg, new Position(new Point(900, 475)));
			
			MessageState.init(world, "PLAYER TWO READY");
			world.pushState(StateEnum.MESSAGE);
		}
	}
}
