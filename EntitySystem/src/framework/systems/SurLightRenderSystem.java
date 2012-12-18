package framework.systems;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import helpers.Color;
import helpers.Draw;
import helpers.Point;
import engine.GLEngine;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.World;
import framework.components.Angle;
import framework.components.Circle;
import framework.components.ColorComp;
import framework.components.Item;
import framework.components.Obstacle;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Velocity;

public class SurLightRenderSystem extends CoreSystem{

	public SurLightRenderSystem(World w) {
		super(w);
	}

	public void run(EntityManager em)
	{
		Entity worldComp = em.getByStringID("camera");
		Point trans = new Point();

		if (world != null)
			trans = em.getComponent(worldComp, Position.class).position.neg();

		glPushMatrix();
		Draw.translate(trans);

		for (Entity e : em.getEntityAll(Tex.class, Obstacle.class, Polygon.class))
		{
			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			if (em.hasComponent(e, Angle.class))
				Draw.rotate(em.getComponent(e, Angle.class).angle);


			Tex t = em.getComponent(e, Tex.class);
			t.render(world, e);

			glPopMatrix();
		}
		glPopMatrix();
	}
}
