package zombies.systems.render;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import org.lwjgl.opengl.GL11;

import zombies.components.Angle;
import zombies.components.ParentTransform;
import zombies.components.Position;
import zombies.components.RenderComponent;
import zombies.components.Scale;
import zombies.utils.Draw;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.managers.EntityManager;

public class RenderSystem extends CoreSystem {
	
	private void transform(CoreEntity e)
	{
		EntityManager em = world.getEntityManager();
		if (em.hasComponent(e, ParentTransform.class))
		{
			CoreEntity parent = em.getComponent(e, ParentTransform.class).parent;
			transform(parent);
		}
		
		if (em.hasComponent(e, Position.class))
		{
			Position pos = em.getComponent(e, Position.class);
			if (!pos.local)
				Draw.translate(world.camera.neg());
			Draw.translate(pos.position);
		}

		if (em.hasComponent(e, Angle.class))
			Draw.rotate(em.getComponent(e, Angle.class).angle);

		if (em.hasComponent(e, Scale.class))
		{
			double s = em.getComponent(e, Scale.class).scale;
			GL11.glScalef((float)s, (float)s, (float)s);
		}
	}


	@Override
	public void run()
	{
		EntityManager em = world.getEntityManager();
		for (RenderComponent c: em.renders)
		{
			glPushMatrix();

			transform(c.parent);
			
			c.render();

			glPopMatrix();
		}
	}
}