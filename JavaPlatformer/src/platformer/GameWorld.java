package platformer;

import helpers.Collision;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import abstracts.Controllable;
import abstracts.Crashable;
import abstracts.GameObject;
import abstracts.Renderable;
import abstracts.Updateable;

public class GameWorld
{
	List<GameObject> objects = new ArrayList<GameObject>();
	List<Renderable> renders = new ArrayList<Renderable>();
	List<Updateable> updates = new ArrayList<Updateable>();
	List<Controllable> controllers = new ArrayList<Controllable>();
	List<Crashable> crashers = new ArrayList<Crashable>();

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
			crashers.add((Crashable) obj);
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

		for (Crashable c1 : crashers)
			for (Crashable c2 : crashers)
				if (Collision.check(c1, c2))
					c1.handleCollision(c2);
	}

	public void render()
	{
		for (Renderable r : renders)
			r.render(0, 0);
	}
}
