package engine;

import helpers.Time;
import states.Level1State;
import states.CutsceneState;
import states.Level2State;
import states.MenuState;
import states.State;
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
		world = new World(State.MENU);
		
		MenuState.init(world);
		Level1State.init(world);
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
