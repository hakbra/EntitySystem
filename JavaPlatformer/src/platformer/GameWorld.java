package platformer;

import helpers.Collision;
import interfaces.Controllable;
import interfaces.Crashable;
import interfaces.Crasher;
import interfaces.Renderable;
import interfaces.Updateable;

import java.util.ArrayList;
import java.util.List;

import objects.GameObject;

import org.lwjgl.input.Keyboard;

public class GameWorld
{
	List<GameObject> objects = new ArrayList<GameObject>();
	List<Renderable> renders = new ArrayList<Renderable>();
	List<Updateable> updates = new ArrayList<Updateable>();
	List<Controllable> controllers = new ArrayList<Controllable>();
	List<Crashable> crashables = new ArrayList<Crashable>();
	List<Crasher> crashers = new ArrayList<Crasher>();

	public void addObject(GameObject obj)
	{
		objects.add(obj);

		if (obj instanceof Renderable)
			renders.add((Renderable) obj);

		if (obj instanceof Updateable)
			updates.add((Updateable) obj);

		if (obj instanceof Controllable)
			controllers.add((Controllable) obj);

		if (obj instanceof Crashable)
			crashables.add((Crashable) obj);

		if (obj instanceof Crasher)
			crashers.add((Crasher) obj);
	}

	public void input()
	{
		while (Keyboard.next())
			for (Controllable c : controllers)
				c.input(Keyboard.getEventKey(), Keyboard.getEventKeyState());
	}

	public void update()
	{
		for (Updateable u : updates)
			u.update();

		for (Crasher c1 : crashers)
			for (Crashable c2 : crashables)
				if (Collision.check(c1, c2))
				{
					c1.handleCollision(c2);
					c2.handleCollision(c1);
				}
	}

	public void render()
	{
		for (Renderable r : renders)
			r.render(0, 0);
	}
}
