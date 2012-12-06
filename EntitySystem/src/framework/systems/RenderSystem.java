package framework.systems;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import helpers.Draw;
import helpers.State;
import framework.CoreSystem;
import framework.Entity;
import framework.EntityManager;
import framework.components.Angle;
import framework.components.Position;
import framework.components.Render;

public class RenderSystem extends CoreSystem {

	public RenderSystem(EntityManager em)
	{
		super(em);
	}

	@Override
	public void run(EntityManager em)
	{
		for (Entity e : em.getEntity(Render.class))
		{
			Render render = em.getComponent(e, Render.class);

			glPushMatrix();

			if (em.hasComponent(e, Position.class))
				Draw.translate(em.getComponent(e, Position.class).position);

			if (em.hasComponent(e, Angle.class))
				Draw.rotate(em.getComponent(e, Angle.class).angle);
			
			render.render(em, e);

			glPopMatrix();
		}
		
		//em.setState(State.EXIT);
	}
}
