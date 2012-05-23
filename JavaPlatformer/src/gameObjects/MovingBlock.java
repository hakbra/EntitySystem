package gameObjects;

public class MovingBlock extends Block
{
	int l;
	int x0, y0;
	int d;

	public MovingBlock(int x, int y, int w, int h)
	{
		super(x, y, w, h);

		l = 100;
		x0 = x;
		y0 = y;
		d = 1;
	}

	@Override
	public void update()
	{
		super.update();
		if (x0 - x > l)
			d = 1;
		if (x - x0 > l)
			d = -1;
		x += d;
	}

	@Override
	public void handleCollision(Object obj)
	{
		super.handleCollision(obj);

		if (obj instanceof Player)
		{
			Player p = (Player) obj;
		}
	}
}
