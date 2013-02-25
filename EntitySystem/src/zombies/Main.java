package zombies;

import helpers.Point;
import helpers.Time;
import zombies.states.GameMenuState;
import zombies.states.StartMenuState;
import engine.GLEngine;
import framework.World;
import framework.enums.StateEnum;

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
		GLEngine.init();
		t = new Time();
		world = new World(StateEnum.START_MENU);
		StartMenuState.init(world);
		GameMenuState.init(world);
	}

	public void run()
	{
		while (GLEngine.running() && world.run())
		{
			GLEngine.clearState();
			GLEngine.prepare2D();

			world.runSystems();

			GLEngine.endRender();

			t.sync(30);
		}
	}
}
