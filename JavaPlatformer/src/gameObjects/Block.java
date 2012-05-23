package gameObjects;

import static org.lwjgl.opengl.GL11.glColor3f;
import helpers.Collision;
import abstracts.Crashable;
import abstracts.Rectangle;
import abstracts.Updateable;

public class Block extends Rectangle implements Crashable, Updateable
{
	float color = 0;

	public Block(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void setColor(int n)
	{
		color = n;
	}

	@Override
	public void render(int dx, int dy)
	{
		glColor3f(1f, color, 0f);
		super.render(dx, dy);
	}

	@Override
	public void handleCollision(Object obj)
	{
		if (obj instanceof Player)
		{
			Player p = (Player) obj;

			int d = Collision.blockPlayerReaction(this, (Player) obj);
			p.setJumpTrue();
		}
	}

	@Override
	public void update()
	{
		color = 0;
	}
}
