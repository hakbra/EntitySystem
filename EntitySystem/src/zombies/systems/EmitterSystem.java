package zombies.systems;


import java.util.Random;

import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Collider;
import framework.components.CollisionCircle;
import framework.components.Emitter;
import framework.components.Particle;
import framework.components.Position;
import framework.components.RenderCircle;
import framework.components.Timer;
import framework.components.Velocity;
import framework.enums.LayerEnum;
import framework.managers.EntityManager;
import framework.utils.Color;
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
				em.addComponent(particle, new RenderCircle(size, c).setLayer(LayerEnum.MOVER));
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
