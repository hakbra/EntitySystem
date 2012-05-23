package gameObjects;

import static org.lwjgl.opengl.GL11.glColor3f;

import org.lwjgl.input.Keyboard;

import abstracts.Controllable;
import abstracts.Rectangle;
import abstracts.Updateable;

public class Player extends Rectangle implements Controllable, Updateable
{
	static int s = 6;

	int vx, vy;
	int kx, ky;

	boolean jump;

	public Player(int x, int y)
	{
		this.x = x;
		this.y = y;

		w = 16;
		h = 32;

		vx = 0;
		vy = 0;

		kx = 0;
		ky = 0;

		jump = false;
	}

	@Override
	public void render(int dx, int dy)
	{
		glColor3f(1f, 1f, 1f);
		super.render(dx, dy);
	}

	@Override
	public void input(int k, boolean pressed)
	{
		if (pressed)
			switch (k)
			{
			case Keyboard.KEY_RIGHT:
			case Keyboard.KEY_D:
				kx += s;
				break;
			case Keyboard.KEY_LEFT:
			case Keyboard.KEY_A:
				kx -= s;
				break;
			case Keyboard.KEY_UP:
				if (jump)
					vy += s * 3;
				break;
			case Keyboard.KEY_W:
				ky += s;
				break;
			case Keyboard.KEY_S:
				ky -= s;
				break;
			}
		else
			switch (k)
			{
			case Keyboard.KEY_RIGHT:
			case Keyboard.KEY_D:
				kx -= s;
				break;
			case Keyboard.KEY_LEFT:
			case Keyboard.KEY_A:
				kx += s;
				break;
			case Keyboard.KEY_W:
				ky -= s;
				break;
			case Keyboard.KEY_S:
				ky += s;
				break;
			}

	}

	@Override
	public void update()
	{
		x += kx;
		y += ky;

		x += vx;
		y += vy;

		if (vy > 20)
			vy = 20;

		jump = false;
	}

	@Override
	public void handleCollision(Object obj)
	{
		if (obj instanceof Block)
			((Block) obj).setColor(1);
	}

	public void setVx(int vx)
	{
		this.vx = vx;
	}

	public void setVy(int vy)
	{
		this.vy = vy;
	}

	public int getVx()
	{
		return vx + kx;
	}

	public int getVy()
	{
		return vy + ky;
	}

	public void setJumpTrue()
	{
		jump = true;
	}

}
