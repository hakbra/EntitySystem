package framework.systems.input;

import helpers.Point;
import helpers.Time;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Angle;
import framework.components.AngleSpeed;
import framework.components.Bullet;
import framework.components.CollisionCircle;
import framework.components.Collider;
import framework.components.Damage;
import framework.components.DestroyOnImpact;
import framework.components.EmitterOnImpact;
import framework.components.Gun;
import framework.components.KeyInput;
import framework.components.CollisionPolygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Timer;
import framework.components.Velocity;
import framework.enums.LayerEnum;
import framework.managers.EntityManager;




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
					bullet.components.add(new Tex("bullet.png", new Point(10, 10)).setLayer(LayerEnum.MOVER));
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
