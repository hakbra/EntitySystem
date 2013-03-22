package zombies.systems;


import java.util.Random;

import zombies.components.Collider;
import zombies.components.CollisionCircle;
import zombies.components.Emitter;
import zombies.components.Particle;
import zombies.components.Position;
import zombies.components.RenderCircle;
import zombies.components.Timer;
import zombies.components.Velocity;
import zombies.utils.Color;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.DynEnum;
import framework.managers.EntityManager;
import framework.utils.Point;
import framework.utils.Time;

public class EmitterSystem extends CoreSystem{


	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		for (CoreEntity e : em.getEntity(Emitter.class))
		{
			Point position 	= 	em.getComponent(e, Position.class).position;

			for (int i = 0; i < 4; i++)
			{
				Random r = new Random();

				Color c = new Color(1, r.nextFloat(), 0);
				float angle = r.nextInt() % 360;
				float speed =  r.nextFloat() * 4;
				int size = 3 + r.nextInt(2);
				int time = 150 + r.nextInt(100);

				CoreEntity particle = new CoreEntity();
				particle.name = "particle";
				em.addComponent(particle, new Position(new Point(position)));
				em.addComponent(particle, new Velocity(new Point(angle), speed));
				em.addComponent(particle, new RenderCircle(size, c).setLayer(DynEnum.at("layer").get("mover")));
				em.addComponent(particle, new CollisionCircle(size));
				em.addComponent(particle, new Timer(time));
				em.addComponent(particle, new Particle());
				em.addComponent(particle, new Collider(1));
			}
		}

		for (CoreEntity e : em.getEntity(Particle.class))
		{
			Color c 	= 	em.getComponent(e, RenderCircle.class).color;
			Timer timer = 	em.getComponent(e, Timer.class);

			float elapsed = (Time.getTime() - timer.start);
			float full = timer.time;

			c.alpha = (float) (1 - elapsed/full);
		}
	}
}
