package gameObjects;

import helpers.Collision;
import interfaces.MotionInterface;
import interfaces.RectangleInterface;
import objects.Motion;

public class MovingBlock extends Block
{
	public final Motion motion;
	int x0, y0, lx, ly;
	boolean playerOn;

	public MovingBlock(int x, int y, int w, int h)
	{
		super(x, y, w, h);
		motion = new Motion();

		lx = 200;
		x0 = x;
		ly = 100;
		y0 = y;

		playerOn = false;
	}

	@Override
	public void update()
	{
		super.update();

		if (rectangle.getX() == x0 + lx)
			motion.setVX(-1);
		else if (rectangle.getX() == x0)
			motion.setVX(1);

		if (rectangle.getY() == y0 + ly)
			motion.setVY(-1);
		else if (rectangle.getY() == y0)
			motion.setVY(1);

		if (playerOn)
			rectangle.move(motion);

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
					((Player) obj).getRectangle().addX(motion.getVX());
					if (motion.getVY() < 0)
						((Player) obj).getRectangle().addY(motion.getVY());

					playerOn = true;
				}
			}
		}
	}
}
