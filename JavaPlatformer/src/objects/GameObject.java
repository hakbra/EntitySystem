package objects;

import platformer.GameWorld;

public class GameObject
{
	GameWorld parent;

	public GameWorld getParent()
	{
		return parent;
	}

	public void setParent(GameWorld gw)
	{
		parent = gw;
	}
}
