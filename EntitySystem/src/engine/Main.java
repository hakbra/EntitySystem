package engine;

import helpers.Time;
import states.GameMenuState;
import states.Level1State;
import states.StartMenuState;
import framework.State;
import framework.World;

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
		world = new World(State.START_MENU);
		
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

			t.sync(60);
		}
	}
}
