package framework.systems;

import helpers.Color;
import helpers.Point;
import helpers.State;
import helpers.Time;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Bullet;
import framework.components.Circle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.DestroyOnImpact;
import framework.components.Emitter;
import framework.components.Gun;
import framework.components.KeyInput;
import framework.components.Position;
import framework.components.Timer;
import framework.components.Velocity;




public class PlayerInputSystem extends CoreSystem{
	
	public static float s = 2f;

	public PlayerInputSystem(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public void run(EntityManager em)
	{
		for (Entity e : em.get(KeyInput.class))
		{
			KeyInput keyInput 	= em.getComponent(e, KeyInput.class);
			Point pos	 		= em.getComponent(e, Position.class).position;
			Point vel 			= em.getComponent(e, Velocity.class).velocity;
			Angle angle 			= em.getComponent(e, Angle.class);
			Gun gun 			= em.getComponent(e, Gun.class);
			Circle circle 		= em.getComponent(e, Circle.class);
			
			if (Keyboard.isKeyDown(keyInput.left))
				angle.angle += 3;
			else if (Keyboard.isKeyDown(keyInput.right))
				angle.angle -= 3;

			if (Keyboard.isKeyDown(keyInput.up))
				vel.set( new Point(angle.angle).mult(4));
			else if (Keyboard.isKeyDown(keyInput.down))
				vel.set( new Point(angle.angle).mult(-4));
			else
				vel.set(0, 0);

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && gun.canFire())
			{
				gun.lastFired = Time.getTime();
				
				Random r = new Random();
				
				for (int i = 0; i < gun.bullets; i++) {
					float deltaAngle = r.nextFloat()*2*gun.spread-gun.spread;
					Point origin = new Point(angle.angle).mult(circle.radius+4);
					Point position = new Point(pos.add(origin));
					int time = 1000 + r.nextInt(200) - 400;
					
					Color c = Color.WHITE;
					
					Entity bullet = new Entity();
					bullet.name = "Bullet";
					em.addComponent(bullet, new Bullet());
					em.addComponent(bullet, new Position(position));
					em.addComponent(bullet, new Velocity(new Point(angle.angle + deltaAngle).mult(gun.speed+(r.nextFloat()-0.5))));
					em.addComponent(bullet, new Circle(3, c));
					em.addComponent(bullet, new Timer(time));
					em.addComponent(bullet, new Damage(gun.damage, e));
					em.addComponent(bullet, new Collider());
					em.addComponent(bullet, new DestroyOnImpact());
					em.addComponent(bullet, new Emitter());
				}
			}
			
		}
	}

}