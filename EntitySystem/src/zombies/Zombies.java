package zombies;

import zombies.states.GameMenuState;
import zombies.states.StartMenuState;
import zombies.utils.Time;
import framework.DynEnum;
import framework.World;
import framework.engine.GLEngine;

public class Zombies
{
	World world;
	Time timer;

	public static void main(String[] args)
	{
		Zombies main = new Zombies();
		main.run();
	}
	
	public void initEnums()
	{
		DynEnum.at("layer").addAll("null", "ground", "item", "mover", "light", "obstacle", "hud", "text");
		DynEnum.at("state").addAll("null", "start_menu", "game_menu", "level1", "level2");
		DynEnum.at("event").addAll("null", "trigger", "damage", "collision", "kill");
	}

	public Zombies()
	{
		initEnums();
		GLEngine.init();
		timer = new Time();
		world = new World();
		
		world.setState(DynEnum.at("state").get("game_menu"));
		GameMenuState.init(world);
		world.setState(DynEnum.at("state").get("start_menu"));
		StartMenuState.init(world);
	}

	public void run()
	{
		while (GLEngine.running() && world.run())
		{
			GLEngine.clearState();
			GLEngine.startRender();

			world.runSystems();

			GLEngine.endRender();

			timer.sync(30);
		}
	}
}
