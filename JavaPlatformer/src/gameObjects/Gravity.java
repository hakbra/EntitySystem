package gameObjects;

import helpers.Point;
import interfaces.Controllable;
import interfaces.Crashable;
import interfaces.MotionInterface;
import objects.GameObject;

import org.lwjgl.input.Keyboard;

public class Gravity extends GameObject implements Crashable, Controllable
{
	boolean active = true;

	@Override
	public void handleCollision(Object obj)
	{
		if (!active)
			return;

		if (obj instanceof MotionInterface)
		{
			((MotionInterface) obj).getMotion().addAY(-1);
		}
	}

	@Override
	public boolean contains(Point p)
	{
		return true;
	}

	@Override
	public void input(int k, boolean pressed)
	{
		if (k == Keyboard.KEY_G && pressed)
			active = !active;
	}
}
