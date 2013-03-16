package zombies.components;

import framework.CoreComponent;

public class Hero extends CoreComponent{

	public int score;
	public int kills;
	public int parts;
	
	public Hero()
	{
		score = 0;
		kills = 0;
		parts = 1;
	}
}
