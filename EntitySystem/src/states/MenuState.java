package states;

import helpers.Point;
import engine.GLEngine;
import framework.Entity;
import framework.World;
import framework.components.Button;
import framework.components.Polygon;
import framework.components.Position;
import framework.components.TextureComp;
import framework.systems.HudRenderSystem;
import framework.systems.MouseInputSystem;

public class MenuState {

	public static void init(World world)
	{
		// Systems
		world.addSystem(new HudRenderSystem(world), State.MENU);
		world.addSystem(new MouseInputSystem(world), State.MENU);
		
		//Buttons
		Entity screenButton = new Entity();
		screenButton.name = "screenButton";
		screenButton.components.add(Polygon.rectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(GLEngine.WIDTH - 125, 25)));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new TextureComp("button.png"));
		world.addEntity(screenButton, State.MENU);

		Entity runButton = new Entity();
		runButton.name = "runButton";
		runButton.components.add(Polygon.rectangle(new Point(200, 100)));
		runButton.components.add(new Position(new Point(500, 300)));
		runButton.components.add(new Button("Level 1"));
		runButton.components.add(new TextureComp("button.png"));
		world.addEntityAndID(runButton, State.MENU);

		Entity exitButton = new Entity();
		exitButton.name = "exitButton";
		exitButton.components.add(Polygon.rectangle(new Point(200, 100)));
		exitButton.components.add(new Position(new Point(500, 150)));
		exitButton.components.add(new Button("Exit"));
		exitButton.components.add(new TextureComp("button.png"));
		world.addEntity(exitButton, State.MENU);
	}
	
}
