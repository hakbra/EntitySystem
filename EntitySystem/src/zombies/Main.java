package zombies;

import zombies.states.GameMenuState;
import zombies.states.StartMenuState;
import framework.DynEnum;
import framework.World;
import framework.engine.GLEngine;
import framework.utils.Time;

public class Main
{
	World world;
	Time t;

	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}

	public Main()
	{
		DynEnum.at("layer").addAll("null", "ground", "item", "mover", "light", "obstacle", "hud", "text");
		DynEnum.at("state").addAll("null", "start_menu", "game_menu", "level1", "level2");
		DynEnum.at("event").addAll("null", "trigger", "damage", "collision", "kill");
		
		GLEngine.init();
		t = new Time();
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

			t.sync(30);
		}
	}
}
