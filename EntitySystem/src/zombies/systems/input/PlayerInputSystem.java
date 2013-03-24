package zombies.systems.input;


import java.util.Random;

import org.lwjgl.input.Keyboard;

import zombies.components.Angle;
import zombies.components.AngleSpeed;
import zombies.components.Bullet;
import zombies.components.Collider;
import zombies.components.CollisionCircle;
import zombies.components.Damage;
import zombies.components.DestroyOnImpact;
import zombies.components.EmitterOnImpact;
import zombies.components.Gun;
import zombies.components.KeyInput;
import zombies.components.Position;
import zombies.components.Tex;
import zombies.components.Timer;
import zombies.components.Velocity;
import zombies.utils.Time;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.DynEnum;
import framework.managers.EntityManager;
import framework.utils.Point;

public class PlayerInputSystem extends CoreSystem{
	
	public static float s = 2f;
	
	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		for (CoreEntity e : em.getEntity(KeyInput.class))
		{
			KeyInput keyInput 	= em.getComponent(e, KeyInput.class);
			Point pos	 		= em.getComponent(e, Position.class).position;
			Point vel 			= em.getComponent(e, Velocity.class).dir;
			Angle angle 		= em.getComponent(e, Angle.class);
			AngleSpeed angleS 	= em.getComponent(e, AngleSpeed.class);
			Gun gun 			= em.getComponent(e, Gun.class);
			CollisionCircle circle 		= em.getComponent(e, CollisionCircle.class);
			
			if (Keyboard.isKeyDown(keyInput.left))
				angleS.speed = 6;
			else if (Keyboard.isKeyDown(keyInput.right))
				angleS.speed = -6;
			else
				angleS.speed = 0;

			if (Keyboard.isKeyDown(keyInput.up))
				vel.set( new Point(angle.angle).mult(4));
			else if (Keyboard.isKeyDown(keyInput.down))
				vel.set( new Point(angle.angle).mult(-4));
			else
				vel.set(0, 0);

			if (Keyboard.isKeyDown(keyInput.shoot) && gun.canFire())
			{
				gun.lastFired = Time.getTime();
				
				Random r = new Random();
				
				for (int i = 0; i < gun.bullets; i++) {
					float deltaAngle = r.nextFloat()*2*gun.spread-gun.spread;
					Point origin = new Point(angle.angle).mult(circle.getRadius());
					Point position = new Point(pos.add(origin));
					int time = 500 + r.nextInt(100);
					float speed = gun.speed + r.nextFloat();
					
					CoreEntity bullet = new CoreEntity();
					bullet.name = "Bullet" + Time.getTime();
					bullet.components.add(new Bullet(e));
					bullet.components.add(new Position(position));
					bullet.components.add(new Velocity(new Point(angle.angle + deltaAngle), speed));
					bullet.components.add(new CollisionCircle(5));
					bullet.components.add(new Tex("bullet.png", new Point(10, 10)).setLayer(DynEnum.at("layer").get("mover")));
					bullet.components.add(new Timer(time));
					bullet.components.add(new Angle(angle.angle + deltaAngle));
					bullet.components.add(new Damage(gun.damage, e));
					bullet.components.add(new Collider(2));
					bullet.components.add(new DestroyOnImpact());
					bullet.components.add(new EmitterOnImpact());
					em.addEntity(bullet);
				}
			}
		}
	}

}
