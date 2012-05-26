package gameObjects;

import helpers.Collision;
import interfaces.MotionInterface;
import interfaces.RectangleInterface;
import objects.Motion;

public class MovingBlock extends Block implements MotionInterface
{
	public final Motion motion;
	int x0, lx;
	boolean playerOn;

	public MovingBlock(int x, int y, int w, int h)
	{
		super(x, y, w, h);
		motion = new Motion();

		lx = 200;
		x0 = x;

		playerOn = false;
	}

	@Override
	public void update()
	{
		super.update();

		rectangle.move(motion);

		if (rectangle.getX() == x0 + lx)
			motion.setVX(-1);
		else if (rectangle.getX() == x0)
			motion.setVX(1);

		playerOn = false;
	}

	@Override
	public void handleCollision(Object obj)
	{
		if (obj instanceof MotionInterface)
		{
			if (obj instanceof RectangleInterface)
			{
				int d = Collision.blockPlayerReaction((RectangleInterface) this, (RectangleInterface) obj);
				if (d == 1 && obj instanceof Player)
				{
					((Player) obj).setOnGroundTrue();
					((Player) obj).getMotion().setRX(motion.getVX());

					playerOn = true;
				}
			}
		}
	}

	@Override
	public Motion getMotion()
	{
		return motion;
	}
}
