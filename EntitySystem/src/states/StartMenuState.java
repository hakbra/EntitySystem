package states;

import helpers.Point;
import engine.GLEngine;
import framework.Entity;
import framework.Layer;
import framework.State;
import framework.World;
import framework.components.Button;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.Tex;
import framework.systems.LightSystem;
import framework.systems.input.MouseInputSystem;
import framework.systems.render.RenderSystem;

public class StartMenuState {

	public static void init(World world)
	{
		// Systems
		world.addSystem(new LightSystem(world), State.START_MENU);
		world.addSystem(new RenderSystem(world), State.START_MENU);
		world.addSystem(new MouseInputSystem(world), State.START_MENU);
		
		//Buttons
		Entity screenButton = new Entity();
		screenButton.name = "screenButton";
		screenButton.layer = Layer.HUD;
		screenButton.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(GLEngine.WIDTH - 125, 25)));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Tex("button.png"));
		world.addEntity(screenButton, State.START_MENU);

		Entity runButton = new Entity();
		runButton.name = "runButton";
		runButton.layer = Layer.HUD;
		runButton.components.add(Polygon.rectangle(new Point(200, 100)));
		runButton.components.add(new Position(new Point(500, 300)));
		runButton.components.add(new Button("Play"));
		runButton.components.add(new Tex("button.png"));
		world.addEntity(runButton, State.START_MENU);
		world.registerID(runButton, State.START_MENU);

		Entity exitButton = new Entity();
		exitButton.name = "exitButton";
		exitButton.layer = Layer.HUD;
		exitButton.components.add(Polygon.rectangle(new Point(200, 100)));
		exitButton.components.add(new Position(new Point(500, 150)));
		exitButton.components.add(new Button("Exit"));
		exitButton.components.add(new Tex("button.png"));
		world.addEntity(exitButton, State.START_MENU);
	}
	
}
