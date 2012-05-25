package gameObjects;

import static org.lwjgl.opengl.GL11.glColor3f;
import helpers.Point;
import interfaces.Controllable;
import interfaces.Crasher;
import interfaces.MotionInterface;
import interfaces.RectangleInterface;
import interfaces.Renderable;
import interfaces.Updateable;

import java.util.ArrayList;

import objects.GameObject;
import objects.Motion;
import objects.Rectangle;

import org.lwjgl.input.Keyboard;

public class Player extends GameObject implements Controllable, Updateable, Renderable, Crasher, RectangleInterface, MotionInterface
{
	static int s = 2;

	Rectangle rectangle;
	Motion motion;

	boolean onGround, keyRight, keyLeft;

	public Player(int x, int y)
	{
		rectangle = new Rectangle(x, y, 16, 32);
		motion = new Motion();

		onGround = false;
		keyRight = false;
		keyLeft = false;
	}

	@Override
	public void render(int dx, int dy)
	{
		glColor3f(1f, 1f, 1f);
		rectangle.render(dx, dy);
	}

	@Override
	public void input(int k, boolean pressed)
	{
		if (pressed)
			switch (k)
			{
			case Keyboard.KEY_RIGHT:
				keyRight = true;
				break;
			case Keyboard.KEY_LEFT:
				keyLeft = true;
				break;
			case Keyboard.KEY_UP:
				if (onGround)
					motion.addVY(s * 10);
				break;
			case Keyboard.KEY_R:
				rectangle.setX(500);
				rectangle.setY(500);
				motion = new Motion();
				break;
			}
		else
			switch (k)
			{
			case Keyboard.KEY_RIGHT:
				keyRight = false;
				break;
			case Keyboard.KEY_LEFT:
				keyLeft = false;
				break;
			}

	}

	@Override
	public void update()
	{
		if (keyRight)
			motion.addAX(s);
		if (keyLeft)
			motion.addAX(-s);

		if (motion.getVX() > 0)
			motion.addAX(-1);
		else if (motion.getVX() < 0)
			motion.addAX(1);

		motion.update(onGround);

		rectangle.move(motion);

		onGround = false;

		motion.setAX(0);
		motion.setAY(0);
	}

	@Override
	public void handleCollision(Object obj)
	{
		if (obj instanceof Block)
			((Block) obj).setColor(1);
	}

	public void setOnGroundTrue()
	{
		onGround = true;
	}

	@Override
	public ArrayList<Point> getPoints()
	{
		return rectangle.getPoints();
	}

	@Override
	public Rectangle getRectangle()
	{
		return rectangle;
	}

	@Override
	public Motion getMotion()
	{
		return motion;
	}

}
