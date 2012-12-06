package framework.components;

import framework.Entity;
import framework.EntityManager;

public interface Render {

	public void render(EntityManager em, Entity e);
}
