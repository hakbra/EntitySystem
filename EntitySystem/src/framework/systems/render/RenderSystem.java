package framework.systems.render;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import org.lwjgl.opengl.GL11;

import framework.CoreComponent;
import framework.CoreEntity;
import framework.CoreSystem;
import framework.World;
import framework.components.Angle;
import framework.components.Button;
import framework.components.CollisionCircle;
import framework.components.CollisionPolygon;
import framework.components.ColorComp;
import framework.components.Health;
import framework.components.Hero;
import framework.components.ParentTransform;
import framework.components.Position;
import framework.components.Scale;
import framework.components.Tex;
import framework.engine.GLEngine;
import framework.helpers.Color;
import framework.helpers.Draw;
import framework.helpers.Point;
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
		for (CoreComponent c: em.renders)
		{
			glPushMatrix();

			transform(c.parent);
			
			c.render();

			glPopMatrix();
		}
	}
}