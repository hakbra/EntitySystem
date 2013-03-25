package zombies;

import zombies.states.GameMenuState;
import zombies.states.StartMenuState;
import zombies.utils.Time;
import framework.DynEnum;
import framework.World;
import framework.engine.GLEngine;

public class Zombies
{
	World world = new World();
	Time timer = new Time();

	public static void main(String[] args)
	{
		Zombies zombies = new Zombies();
		zombies.init();
		zombies.run();
	}
	
	public void init()
	{
		DynEnum.at("layer").addAll("ground", "item", "mover", "light", "obstacle", "hud", "text");
		DynEnum.at("state").addAll("start_menu", "game_menu", "level1", "level2");
		DynEnum.at("event").addAll("trigger", "damage", "collision", "kill");
		
		world.setState(DynEnum.at("state").get("game_menu"));
		GameMenuState.init(world);
		world.setState(DynEnum.at("state").get("start_menu"));
		StartMenuState.init(world);
		
		GLEngine.init();
	}

	public void run()
	{
		while (GLEngine.run() && world.run())
		{
			GLEngine.clearState();
			GLEngine.startRender();

			world.runSystems();

			GLEngine.endRender();

			timer.sync(30);
		}
	}
}
