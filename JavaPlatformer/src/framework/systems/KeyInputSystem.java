package framework.systems;

import helpers.Point;
import helpers.Time;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import framework.Component;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Bullet;
import framework.components.Circle;
import framework.components.Damage;
import framework.components.Gun;
import framework.components.KeyInput;
import framework.components.Position;
import framework.components.Timer;
import framework.components.Velocity;




public class KeyInputSystem extends CoreSystem{
	
	public static float s = 2f;

	public KeyInputSystem(EntityManager em, Class<? extends Component>... types)
	{
		super(em, types);
	}
	
	@Override
	public void run(EntityManager em)
	{
		for (Entity e : this.entities())
		{
			KeyInput keyInput 	= em.getComponent(e, KeyInput.class);
			Point pos	 		= em.getComponent(e, Position.class).position;
			Point vel 			= em.getComponent(e, Velocity.class).velocity;
			Angle a 			= em.getComponent(e, Angle.class);
			Gun gun 			= em.getComponent(e, Gun.class);
			Circle circle 		= em.getComponent(e, Circle.class);
			
			if (Keyboard.isKeyDown(keyInput.left))
				a.angle += 3;
			else if (Keyboard.isKeyDown(keyInput.right))
				a.angle -= 3;

			if (Keyboard.isKeyDown(keyInput.up))
				vel.set( new Point(a.angle).mult(4));
			else if (Keyboard.isKeyDown(keyInput.down))
				vel.set( new Point(a.angle).mult(-4));
			else
				vel.set(0, 0);

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && gun.canFire())
			{
				gun.lastFired = Time.getTime();
				
				Random r = new Random();
				
				for (int i = 0; i < gun.bullets; i++) {
					float deltaAngle = r.nextFloat()*2*gun.spread-gun.spread;
					Point origin = new Point(a.angle).mult(10);
					
					Entity bullet = em.newEntity();
					bullet.name = "Bullet";
					em.addComponent(bullet, new Bullet());
					em.addComponent(bullet, new Position(new Point(pos.add(origin))));
					em.addComponent(bullet, new Velocity(new Point(a.angle + deltaAngle).mult(gun.speed)));
					em.addComponent(bullet, new Circle(3, "Green"));
					em.addComponent(bullet, new Timer(1000 + r.nextInt(200) - 400));
					em.addComponent(bullet, new Damage(gun.damage, e));
				}
			}
			
		}
	}

}
