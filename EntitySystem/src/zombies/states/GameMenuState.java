package zombies.states;

import helpers.Point;
import engine.GLEngine;
import framework.CoreEntity;
import framework.World;
import framework.components.Button;
import framework.components.CollisionPolygon;
import framework.components.Position;
import framework.components.Tex;
import framework.components.Text;
import framework.enums.LayerEnum;
import framework.enums.StateEnum;
import framework.systems.LightSystem;
import framework.systems.input.MouseInputSystem;
import framework.systems.render.RenderSystem;

public class GameMenuState {

	public static void init(World world)
	{
		world.clearState(StateEnum.GAME_MENU);
		
		// Systems
		world.addSystem(new LightSystem(world), StateEnum.GAME_MENU);
		world.addSystem(new RenderSystem(world), StateEnum.GAME_MENU);
		world.addSystem(new MouseInputSystem(world), StateEnum.GAME_MENU);
		
		//Buttons
		CoreEntity screenButton = new CoreEntity();
		screenButton.name = "screenButton";
		screenButton.layer = LayerEnum.HUD;
		screenButton.components.add(CollisionPolygon.centerRectangle(new Point(100, 50)));
		screenButton.components.add(new Position(new Point(GLEngine.WIDTH - 75, 50)));
		screenButton.components.add(new Button("Screen"));
		screenButton.components.add(new Text("Screen").setLayer(LayerEnum.TEXT));
		screenButton.components.add(new Tex("button.png", new Point(100, 50)).setLayer(LayerEnum.HUD));
		world.addEntity(screenButton, StateEnum.GAME_MENU);

		CoreEntity runButton = new CoreEntity();
		runButton.name = "runButton";
		runButton.layer = LayerEnum.HUD;
		runButton.components.add(CollisionPolygon.centerRectangle(new Point(200, 100)));
		runButton.components.add(new Position(new Point(GLEngine.WIDTH/2, 400)));
		runButton.components.add(new Button("Resume"));
		runButton.components.add(new Text("Resume").setLayer(LayerEnum.TEXT));
		runButton.components.add(new Tex("button.png", new Point(200, 100)).setLayer(LayerEnum.HUD));
		world.addEntity(runButton, StateEnum.GAME_MENU);
		world.registerID(runButton, StateEnum.GAME_MENU);

		CoreEntity exitButton = new CoreEntity();
		exitButton.name = "exitButton";
		exitButton.layer = LayerEnum.HUD;
		exitButton.components.add(CollisionPolygon.centerRectangle(new Point(200, 100)));
		exitButton.components.add(new Position(new Point(GLEngine.WIDTH/2, 250)));
		exitButton.components.add(new Button("Exit"));
		exitButton.components.add(new Text("Exit").setLayer(LayerEnum.TEXT));
		exitButton.components.add(new Tex("button.png", new Point(200, 100)).setLayer(LayerEnum.HUD));
		world.addEntity(exitButton, StateEnum.GAME_MENU);
	}
	
}
