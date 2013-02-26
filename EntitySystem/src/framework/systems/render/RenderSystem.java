package framework.systems.render;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import helpers.Color;
import helpers.Draw;
import helpers.Point;

import org.lwjgl.opengl.GL11;

import engine.GLEngine;
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
import framework.managers.EntityManager;

public class RenderSystem extends CoreSystem {

	public RenderSystem(World w)
	{
		super(w);
	}
	
	private void transform(EntityManager em, CoreEntity e, Point camPos)
	{
		if (em.hasComponent(e, ParentTransform.class))
		{
			CoreEntity parent = em.getComponent(e, ParentTransform.class).parent;
			transform(em, parent, camPos);
		}
		
		if (em.hasComponent(e, Position.class))
		{
			Position pos = em.getComponent(e, Position.class);
			if (!pos.local)
				Draw.translate(camPos);
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
	public void run(EntityManager em)
	{
		CoreEntity cam = em.getByStringID("camera");
		Point camPos = new Point();
		if (cam != null)
			camPos = em.getComponent(cam, Position.class).position.neg();
		
		for (CoreComponent c: em.renders)
		{
			glPushMatrix();

			transform(em, c.parent, camPos);
			
			c.render();

			glPopMatrix();
		}
	}
}