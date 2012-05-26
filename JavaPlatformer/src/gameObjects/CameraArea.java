package gameObjects;

import helpers.Point;
import interfaces.Crashable;
import objects.GameObject;
import platformer.Main;

public class CameraArea extends GameObject implements Crashable
{
	int right;
	int left;

	public CameraArea()
	{
		left = Main.WIDTH / 3;
		right = Main.WIDTH * 2 / 3;
	}

	@Override
	public boolean contains(Point p)
	{
		Point p2 = new Point(p.x - this.getParent().getDeltaX(), p.y - this.getParent().getDeltaY());
		if (p2.x < left || p2.x > right)
			return true;
		return false;
	}

	@Override
	public void handleCollision(Object obj)
	{
		if (obj instanceof Player)
		{
			int x = ((Player) obj).getRectangle().getX() - getParent().getDeltaX();
			System.out.println("X: " + x);
			int delta = 0;
			if (x > right)
				delta = x - right;
			else if (x < left)
				delta = x - left;
			this.getParent().addDX(delta);
		}
	}
}
