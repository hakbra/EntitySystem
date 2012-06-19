package gameObjects;

import helpers.Point;
import interfaces.Crashable;
import objects.GameObject;
import platformer.Main;

public class CameraArea extends GameObject implements Crashable
{
	int right;
	int left;
	int top;
	int bottom;

	public CameraArea()
	{
		left = Main.WIDTH / 3;
		right = Main.WIDTH * 2 / 3;
		top = Main.HEIGHT * 2 / 3;
		bottom = Main.HEIGHT / 3;
	}

	@Override
	public boolean contains(Point p)
	{
		Point p2 = new Point(p.x - this.getParent().getDeltaX(), p.y - this.getParent().getDeltaY());
		if (p2.x < left || p2.x > right)
			return true;
		if (p2.y < bottom || p2.y > top)
			return true;
		return false;
	}

	@Override
	public void handleCollision(Object obj)
	{
		if (obj instanceof Player)
		{
			int x = ((Player) obj).getRectangle().getX() - getParent().getDeltaX();
			int deltaX = 0;
			if (x > right)
				deltaX = x - right;
			else if (x < left)
				deltaX = x - left;
			this.getParent().addDX(deltaX);

			int y = ((Player) obj).getRectangle().getY() - getParent().getDeltaY();
			int deltaY = 0;
			if (y > top)
				deltaY = y - top;
			else if (y < bottom)
				deltaY = y - bottom;
			this.getParent().addDY(deltaY);
		}
	}
}
