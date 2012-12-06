package framework.components;

import helpers.Color;
import helpers.Draw;

import java.util.Random;

import framework.Component;
import framework.Entity;
import framework.EntityManager;



public class Health extends Component{// implements Render{
	
	public float current;
	public float max;
	
	public Health()
	{
		this.current = 100;
		this.max = 100;
		this.name = "Health";
	}

	//@Override
	public void render(EntityManager em, Entity e) {
		/*
		float r = em.getComponent(e, Circle.class).radius;
		float d = 1 - current / max;

		Draw.setColor(Color.GREEN);
		Draw.ring(r, 5);

		Draw.setColor(Color.RED);
		Draw.ring(r, 5, (int) (360 * d));
		*/
	}
}
