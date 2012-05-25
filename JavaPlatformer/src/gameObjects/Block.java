package gameObjects;

import static org.lwjgl.opengl.GL11.glColor3f;
import helpers.Collision;
import helpers.Point;
import interfaces.Crashable;
import interfaces.RectangleInterface;
import interfaces.Renderable;
import interfaces.Updateable;
import objects.GameObject;
import objects.Rectangle;

public class Block extends GameObject implements Crashable, Updateable, Renderable, RectangleInterface
{
	Rectangle rectangle;

	float color = 0;

	public Block(int x, int y, int w, int h)
	{
		rectangle = new Rectangle(x, y, w, h);
	}

	public void setColor(int n)
	{
		color = n;
	}

	@Override
	public void render(int dx, int dy)
	{
		glColor3f(1f, color, 0f);
		rectangle.render(dx, dy);
	}

	@Override
	public void handleCollision(Object obj)
	{
		if (obj instanceof RectangleInterface)
		{
			int d = Collision.blockPlayerReaction((RectangleInterface) this, (RectangleInterface) obj);
			if (d == 1 && obj instanceof Player)
			{
				((Player) obj).setOnGroundTrue();
			}
		}
	}

	@Override
	public void update()
	{
		color = 0;
	}

	@Override
	public boolean contains(Point p)
	{
		return rectangle.contains(p);
	}

	@Override
	public Rectangle getRectangle()
	{
		return rectangle;
	}
}
