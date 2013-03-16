package zombies.systems.render;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import org.lwjgl.opengl.GL11;

import zombies.components.Angle;
import zombies.components.Button;
import zombies.components.CollisionCircle;
import zombies.components.CollisionPolygon;
import zombies.components.ColorComp;
import zombies.components.Health;
import zombies.components.Hero;
import zombies.components.ParentTransform;
import zombies.components.Position;
import zombies.components.Scale;
import zombies.components.Tex;

import framework.CoreComponent;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.engine.GLEngine;
import framework.managers.EntityManager;
import framework.utils.Color;
import framework.utils.Draw;
import framework.utils.Point;

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
		for (CoreComponent c: em.renders)
		{
			glPushMatrix();

			transform(c.parent);
			
			c.render();

			glPopMatrix();
		}
	}
}