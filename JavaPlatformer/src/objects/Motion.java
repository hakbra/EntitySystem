package objects;

public class Motion
{
	int vx, vy;
	int ax, ay;
	int rx;

	public void setRX(int nrx)
	{
		this.rx = nrx;
	}

	public int getRX()
	{
		return rx;
	}

	public int getAX()
	{
		return ax;
	}

	public void setAX(int ax)
	{
		this.ax = ax;
	}

	public int getAY()
	{
		return ay;
	}

	public void addAX(int d)
	{
		this.ax += d;
	}

	public void addAY(int d)
	{
		this.ay += d;
	}

	public void setAY(int ay)
	{
		this.ay = ay;
	}

	public int getVX()
	{
		return vx + rx;
	}

	public void setVX(int x)
	{
		this.vx = x;
	}

	public void addVX(int d)
	{
		this.vx += d;
	}

	public int getVY()
	{
		return vy;
	}

	public void setVY(int y)
	{
		this.vy = y;
	}

	public void addVY(int d)
	{
		this.vy += d;
	}

	public void update(boolean onGround)
	{
		if (onGround)
			if (vx > rx)
				ax -= 1;
			else if (vx < rx)
				ax += 1;

		if (onGround)
			vx += ax;
		vy += ay;

		if (vy < -20)
			vy = -20;

		if (vx > 10)
			vx = 10;
		if (vx < -10)
			vx = -10;

		ax = 0;
		ay = 0;
		rx = 0;
	}
}
